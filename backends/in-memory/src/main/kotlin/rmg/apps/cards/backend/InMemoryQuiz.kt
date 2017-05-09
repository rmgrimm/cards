package rmg.apps.cards.backend

import rmg.apps.cards.base.*
import rmg.apps.cards.base.dsl.findByAll
import rmg.apps.cards.base.model.MultipleChoiceQuestion
import rmg.apps.cards.base.model.Question

data class InMemoryQuiz(val questions: List<Question>) : Quiz<Unit>, List<Question> by questions {

    class Generator(
        val repository: InMemorySignifiedRepository,
        // TODO(rmgrimm): When other question types are available, add them
        val generators : List<Question.Generator<Int, Unit>> = listOf(
            MultipleChoiceQuestion.Generator(repository, InMemoryQuiz.Generator.DEFAULT_NUM_ANSWERS, InMemoryQuiz.Generator.DEFAULT_ANSWER_CRITERIA)
        )
    ) : Quiz.UserUnspecifiedGenerator<Unit> {

        companion object Defaults {
            const val DEFAULT_NUM_ANSWERS = 6
            val DEFAULT_ANSWER_CRITERIA = SignifierCriteria.Any
        }

        override fun generateQuiz(quizLength: Int, questionCriteria: SignifiedCriteria, correctAnswerCriteria: SignifierCriteria): Quiz<Unit> {
            val questionSignifieds = repository
                .findByAll(maxResults = quizLength, order = SignifiedRepository.FindOrder.SPACED_REPETITION, user = Unit) {
                    matches(questionCriteria)
                    contains(correctAnswerCriteria)
                }
                .map { (_, signified) -> signified }

            val questions = questionSignifieds.mapIndexed { _, questionSignified ->
                // TODO(rmgrimm): Use a random generator to generate the question
                generators[0].generateQuestion(questionSignified) {
                    // TODO(rmgrimm): Do something with the handler? Possibly with the index of the question?
                }
            }

            return InMemoryQuiz(questions)
        }
    }

    init {
        if (questions.isEmpty()) {
            throw IllegalArgumentException("InMemoryQuiz must have questions")
        }
    }

    private var questionIterator = questions.listIterator()

    override val userId: Unit
        get() = Unit

    override val state: Quiz.State
        get() {
            if (!questionIterator.hasNext()) {
                return Quiz.State.COMPLETED
            }

            if (!questionIterator.hasPrevious()) {
                return Quiz.State.UNSTARTED
            }

            return Quiz.State.STARTED
        }

    override fun hasNextQuestion(): Boolean {
        return questionIterator.hasNext()
    }

    override fun nextQuestion(): Question {
        return questionIterator.next()
    }
}
