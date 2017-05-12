package rmg.apps.cards.base.model

import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.SignifierCriteria
import rmg.apps.cards.base.dsl.findByAll
import kotlin.js.JsName

sealed class Question {

    interface Generator<T, in U> {
        val repository: SignifiedRepository<T, U>

        @JsName("generateQuestion")
        fun generateQuestion(fromSignified: Signified, handler: (Question.() -> Unit)? = null): Question
    }

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
data class MultipleChoiceQuestion(val questionSignified: Signified, val answerSignifiers: List<Signifier>, private val handler: (Question.() -> Unit)? = null) : Question() {

    class Generator<T, in U>(
        override val repository: SignifiedRepository<T, U>,
        val numAnswers: Int,
        val answerCriteria: SignifierCriteria
    ) : Question.Generator<T, U> {
        override fun generateQuestion(fromSignified: Signified, handler: ((Question) -> Unit)?): Question {
            // TODO(rmgrimm): Randomize the signifier that is taken for the question's answer
            val correctAnswer = fromSignified.signifiers.filter(answerCriteria::match).also {
                it.isNotEmpty() || throw IllegalArgumentException("Question signified does not contain any signifiers that can be used as a correct answer!")
            }.first()

            val possibleAnswers = listOf(correctAnswer) + repository.findByAll(maxResults = numAnswers - 1, order = SignifiedRepository.FindOrder.RANDOM) {
                not {
                    equalTo(fromSignified)
                }
                contains(answerCriteria)
            }.map { (_, signified) ->
                // TODO(rmgrimm): Randomize the signifier that is taken for the wrong answer
                signified.signifiers.filter(answerCriteria::match).first()
            }

            // TODO(rmgrimm): Randomize the order of the answer list

            return MultipleChoiceQuestion(fromSignified, possibleAnswers, handler)
        }
    }

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

            this.handler?.invoke(this)
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
