package rmg.apps.cards.backend

import rmg.apps.cards.base.*
import rmg.apps.cards.base.dsl.findByAll
import rmg.apps.cards.base.model.MultipleChoiceQuestion
import rmg.apps.cards.base.model.Question

data class InMemoryQuiz(val questions: List<Question>) : Quiz<Unit>, List<Question> by questions {

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

class InMemoryQuizGenerator(val repository: InMemorySignifiedRepository) : UserUnspecifiedQuizGenerator<Unit> {

    override fun generateQuiz(quizLength: Int, questionCriteria: SignifiedCriteria, answersPerQuestion: Int, answerCriteria: SignifierCriteria): Quiz<Unit> {
        val questionSignifieds = repository
            .findByAll(maxResults = quizLength, order = SignifiedRepository.FindOrder.SPACED_REPETITION, user = Unit) {
                matches(questionCriteria)
                contains(answerCriteria)
            }
            .map { (_, signified) -> signified }

        // TODO(rmgrimm): When other question types are available
        val generators : List<Question.Generator<Int, Unit>> = listOf(
            MultipleChoiceQuestion.Generator(repository, answersPerQuestion, answerCriteria)
        )

        val questions = questionSignifieds.map { questionSignified ->
            generators[0].generateQuestion(questionSignified)
        }

        return InMemoryQuiz(questions)
    }

}

