package rmg.apps.cards.base

import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier
import rmg.apps.cards.base.model.WrittenWord

/**
 * Repository to hold [Signified]s and query against them
 *
 * @param T the type used as an ID for [Signified] objects held in the repository
 * @param U the type used as an ID for users, for purposes of spaced repetition
 */
interface SignifiedRepository<T, in U> : MutableMap<T, Signified> {

    /**
     * Ordering options used when calling [SignifiedRepository.find]
     */
    enum class FindOrder {
        /**
         * No specific order requested
         */
        NONE,

        /**
         * Less known [Signified]s should be ordered before more well-known ones
         */
        SPACED_REPETITION,

        /**
         * Order the [Signified]s randomly
         */
        RANDOM
    }

    /**
     * Add a new [Signified]
     *
     * @param element the new [Signified] to add
     * @return the ID of the new entry
     */
    fun add(element: Signified): T

    /**
     * Add all [Signified]s from a collection
     *
     * @param elements
     */
    fun addAll(elements: Iterable<Signified>): Unit

    /**
     * Find matching [Signified] options
     *
     * @param maxResults the maximum number of results to return
     * @param order how the signified should be ordered when fetching results
     * @param user the user ID to be used for [spaced repetition][FindOrder.SPACED_REPETITION] finds
     * @param criteria a set of [SignifiedCriteria] describing which [Signified]s to return
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    fun find(maxResults: Int? = null, order: FindOrder = FindOrder.NONE, user: U? = null, criteria: SignifiedCriteria = SignifiedCriteria.Any): List<Pair<T, Signified>>
}

/**
 * A sealed class of possible criteria for use with [SignifiedRepository.find] or
 * as predicates for filtering [Signified]
 */
sealed class SignifiedCriteria {

    /**
     * A predicate for filtering in-memory [Signified] objects
     */
    abstract fun match(signified: Signified): Boolean

    /**
     * Criteria to match any [Signified]
     */
    object Any : SignifiedCriteria() {
        override fun match(signified: Signified) = true
    }

    /**
     * Criteria to match no [Signified]
     */
    object None : SignifiedCriteria() {
        override fun match(signified: Signified) = false
    }

    /**
     * Criteria to select based upon [Signified.type]
     */
    data class Type(val type: Signified.Type) : SignifiedCriteria() {
        override fun match(signified: Signified) = signified.type == this.type
    }

    /**
     * Criteria to select based upon a [Signifier] matching the [criteria][SignifierCriteria]
     */
    data class ContainsSignifier(val signifierCriteria: SignifierCriteria) : SignifiedCriteria() {
        override fun match(signified: Signified) = signified.signifiers.any{ signifierCriteria.match(it) }
    }

    data class CompoundCriteria(val left: SignifiedCriteria, val right: SignifiedCriteria, val type: ConjunctionType) : SignifiedCriteria() {
        enum class ConjunctionType {
            AND, OR
        }

        override fun match(signified: Signified) = when (type) {
            ConjunctionType.AND -> left.match(signified) && right.match(signified)
            ConjunctionType.OR -> left.match(signified) || right.match(signified)
        }
    }
}

/**
 * A sealed class of possible criteria for filtering [Signifier]s
 */
sealed class SignifierCriteria {

    /**
     *  A predicate for filtering in-memory [Signifier] objects
     */
    abstract fun match(signifier: Signifier): Boolean

    /**
     *  Criteria to match any [Signifier]
     */
    object Any : SignifierCriteria() {
        override fun match(signifier: Signifier) = true
    }

    /**
     * Criteria to match no [Signifier]
     */
    object None : SignifierCriteria() {
        override fun match(signifier: Signifier) = false
    }

    /**
     * Criteria to select [Signifier]s of type [WrittenWordCriteria]
     *
     * Possible to further filter based upon [locale][WrittenWord.locale] or [weight][WrittenWord.weight].
     *
     * @param locale optional filter for the [locale][Signifier.Locale] of [WrittenWord]
     * @param weight optional filter for the [weight][WrittenWord.weight]
     */
    data class WrittenWordCriteria(val locale: Signifier.Locale? = null, val weight: Int? = null) : SignifierCriteria() {
        override fun match(signifier: Signifier): Boolean {
            if (signifier !is WrittenWord) {
                return false
            }

            if (locale != null && locale doesntMatch signifier.locale) {
                return false
            }

            if (weight != null && weight != signifier.weight) {
                return false
            }

            return true
        }
    }

    /**
     * Criteria to select [Signifier]s of type [Definition]
     *
     * Possible to further fitler based upon [locale][Definition.locale]
     *
     * @param locale optional filter for the [locale][Signifier.Locale] of [Definition]
     */
    data class DefinitionCriteria(val locale: Signifier.Locale? = null) : SignifierCriteria() {
        override fun match(signifier: Signifier): Boolean {
            if (signifier !is Definition) {
                return false
            }

            if (locale != null && locale doesntMatch signifier.locale) {
                return false
            }

            return true
        }
    }

}