package rmg.apps.cards.base

import rmg.apps.cards.base.model.Question

/**
 * Generic quiz interface
 *
 * @param U the type of ID for a User
 */
interface Quiz<U> : List<Question> {

    interface UserSpecificGenerator<U> {
        fun generateQuiz(user: U, quizLength: Int, questionCriteria: SignifiedCriteria, answerCriteria: SignifierCriteria): Quiz<U>
    }
    interface UserUnspecifiedGenerator<U> {
        fun generateQuiz(quizLength: Int, questionCriteria: SignifiedCriteria, correctAnswerCriteria: SignifierCriteria): Quiz<U>
    }

    enum class State {
        /**
         * Indicates the quiz has not finished being generated yet
         */
        GENERATING,

        /**
         * Indicates the quiz has been generated, but not started
         */
        UNSTARTED,

        /**
         * Indicates the quiz has been generated and started, but not yet completed
         */
        STARTED,

        /**
         * Indicates the quiz is complete
         */
        COMPLETED
    }

    val userId: U
    val state: State

    fun hasNextQuestion(): Boolean
    fun nextQuestion(): Question
}
