package rmg.apps.cards.base.model

sealed class Question {
    /**
     * Flag indicating whether the user has answered the question yet. Care should be taken to answer a question only
     * once
     */
    abstract val isAnswered: Boolean

    /**
     * Flag indicating whether a correct answer has been given
     */
    abstract val isCorrect: Boolean?
}

data class MultipleChoiceQuestion(val questionSignified: Signified, val answerSignifiers: List<Signifier>) : Question() {
    var selectedIndex: Int? = null
        set(value) {
            if (field != null) throw IllegalStateException("Selected answer already set!")
            if (value == null) throw IllegalArgumentException("Cannot select null answer")
            if (value !in 0 until answerSignifiers.size) throw IndexOutOfBoundsException("Index must be between 0 and ${answerSignifiers.size}")

            field = value
        }

    override val isAnswered
        get() = selectedIndex != null

    override val isCorrect: Boolean?
        get() {
            val idx = selectedIndex

            return if (idx == null) {
                null
            } else {
                questionSignified.contains(answerSignifiers[idx])
            }
        }
}
