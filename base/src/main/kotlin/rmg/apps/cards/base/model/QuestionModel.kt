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

/**
 * A standard multiple-choice question
 *
 * @param questionSignified the [Signified] that is being tested
 * @param answerSignifiers the list of options given to the user, must contain at least one correct answer
 * @param handler a callback that will be invoked when a selection has been made
 */
data class MultipleChoiceQuestion(val questionSignified: Signified, val answerSignifiers: List<Signifier>, private val handler: ((selectedIndex: Int, correct: Boolean) -> Unit)? = null) : Question() {

    init {
        if (questionSignified.signifiers.intersect(answerSignifiers).isEmpty()) {
            throw IllegalArgumentException("Question has no correct answer! ${questionSignified} does not contain any of ${answerSignifiers}")
        }
    }

    var selectedIndex: Int? = null
        set(value) {
            if (field != null && value != field) throw IllegalStateException("Selected answer already set to ${field}, cannot replace with ${value}!")
            if (value == null) throw IllegalArgumentException("Cannot select null answer")
            if (value !in 0..answerSignifiers.lastIndex) throw IndexOutOfBoundsException("Index must be between 0 and ${answerSignifiers.lastIndex}")

            field = value

            handler?.invoke(value, this.isCorrect!!)
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
