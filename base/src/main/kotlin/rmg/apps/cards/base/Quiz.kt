package rmg.apps.cards.base

import rmg.apps.cards.base.model.Question

/**
 * Generic quiz interface
 *
 * @param U the type of ID for a User
 */
interface Quiz<U> : List<Question> {
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

interface UserSpecificQuizGenerator<U> {
    fun generateQuiz(user: U, quizLength: Int, questionCriteria: SignifiedCriteria, answersPerQuestion: Int, answerCriteria: SignifierCriteria): Quiz<U>
}
interface UserUnspecifiedQuizGenerator<U> {
    fun generateQuiz(quizLength: Int, questionCriteria: SignifiedCriteria, answersPerQuestion: Int, answerCriteria: SignifierCriteria): Quiz<U>
}
