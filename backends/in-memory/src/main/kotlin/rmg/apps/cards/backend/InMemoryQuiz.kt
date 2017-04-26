package rmg.apps.cards.backend

import rmg.apps.cards.base.Quiz
import rmg.apps.cards.base.model.Question

data class InMemoryQuiz(val questions: List<Question>) : Quiz, List<Question> by questions {

    init {
        if (questions.isEmpty()) {
            throw IllegalArgumentException("InMemoryQuiz must have questions")
        }
    }

    private var questionIterator = questions.listIterator()

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

