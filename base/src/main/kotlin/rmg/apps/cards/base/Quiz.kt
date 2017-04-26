package rmg.apps.cards.base

import rmg.apps.cards.base.model.Question

/**
 * Generic quiz interface
 */
interface Quiz : List<Question> {
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

    val state: State

    fun hasNextQuestion(): Boolean
    fun nextQuestion(): Question
}
