package rmg.apps.cards.base

import rmg.apps.cards.base.model.*
import kotlin.js.JsName

/**
 * Repository to hold [Signified]s and query against them
 *
 * @param T the type used as an ID for [Signified] objects held in the repository
 * @param U the type used as an ID for users, for purposes of spaced repetition
 */
interface SignifiedRepository<T, in U> : Map<T, Signified> {

    /**
     * A tuple to keep ID and [Signified] together
     */
    data class StoredSignified<out T>(val id: T, val signified: Signified)

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
     * All available locales within the repository
     */
    val locales: Set<Locale>

    /**
     * WrittenWord locales within the repository
     */
    val writtenWordLocales: Set<Locale>

    /**
     * Definition locales within the repository
     */
    val definitionLocales: Set<Locale>

    /**
     * Find matching [Signified] options
     *
     * @param maxResults the maximum number of results to return
     * @param order how the signified should be ordered when fetching results
     * @param user the user ID to be used for [spaced repetition][FindOrder.SPACED_REPETITION] finds
     * @param criteria a set of [SignifiedCriteria] describing which [Signified]s to return
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    @JsName("find")
    fun find(maxResults: Int? = null,
             order: FindOrder = FindOrder.NONE,
             user: U? = null,
             criteria: SignifiedCriteria = SignifiedCriteria.Any): List<StoredSignified<T>>

    /**
     * Find matching [Signified] options
     *
     * @param maxResults the maximum number of results to return
     * @param order how the signified should be ordered when fetching results
     * @param user the user ID to be used for [spaced repetition][FindOrder.SPACED_REPETITION] finds
     * @param criteria a set of [SignifiedCriteria] describing which [Signified]s to return
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    @JsName("findPagedArray")
    fun findPagedArray(resultsPerPage: Int,
                       maxResults: Int? = null,
                       order: FindOrder = FindOrder.NONE,
                       user: U? = null,
                       criteria: SignifiedCriteria = SignifiedCriteria.Any): PagedArray<StoredSignified<T>>
}

/**
 * A version of [SignifiedRepository] that allows inserting [Signified] in addition to querying
 *
 * @see SignifiedRepository
 */
interface MutableSignifiedRepository<T, in U> : MutableMap<T, Signified>, SignifiedRepository<T, U> {

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
}

/**
 * A sealed class of possible criteria for use with [MutableSignifiedRepository.find] or
 * as predicates for filtering [Signified]
 */
sealed class SignifiedCriteria {

    /**
     * A predicate for filtering in-memory [Signified] objects
     */
    abstract fun match(signified: Signified): Boolean

    /**
     * A predicate for testing the criteria
     */
    open fun contains(signifiedCriteria: SignifiedCriteria): Boolean = this == signifiedCriteria

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
     * Criteria that tests equality
     */
    data class EqualTo(val signified: Signified) : SignifiedCriteria() {
        override fun match(signified: Signified) = this.signified == signified
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
        override fun match(signified: Signified) = signified.signifiers.any { signifierCriteria.match(it) }
    }

    /**
     * Criteria that negates other criteria
     */
    data class Not(val criteria: SignifiedCriteria) : SignifiedCriteria() {
        override fun match(signified: Signified) = !criteria.match(signified)
    }

    /**
     * Criteria to join other criteria together
     */
    data class CompoundCriteria(val left: SignifiedCriteria, val right: SignifiedCriteria, val type: ConjunctionType) : SignifiedCriteria() {
        enum class ConjunctionType {
            AND, OR
        }

        override fun match(signified: Signified) = when (type) {
            ConjunctionType.AND -> left.match(signified) && right.match(signified)
            ConjunctionType.OR -> left.match(signified) || right.match(signified)
        }

        override fun contains(signifiedCriteria: SignifiedCriteria): Boolean = this == signifiedCriteria || left.contains(signifiedCriteria) || right.contains(signifiedCriteria)
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
     * @param locale optional filter for the [locale][Locale] of [WrittenWord]
     * @param localeStrict flag indicating whether the locale should match strictly
     * @param weight optional filter for the [weight][WrittenWord.weight]
     */
    data class WrittenWordCriteria(val locale: Locale? = null, val localeStrict: Boolean = false, val weight: Int? = null) : SignifierCriteria() {
        override fun match(signifier: Signifier): Boolean {
            if (signifier !is WrittenWord) {
                return false
            }

            if (locale != null) {
                if (localeStrict && locale strictDoesntMatch signifier.locale) {
                    return false
                } else if (!localeStrict && locale doesntMatch signifier.locale) {
                    return false
                }
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
     * @param locale optional filter for the [locale][Locale] of [Definition]
     * @param localeStrict flag indicating whether the locale should match strictly
     */
    data class DefinitionCriteria(val locale: Locale? = null, val localeStrict: Boolean = false) : SignifierCriteria() {
        override fun match(signifier: Signifier): Boolean {
            if (signifier !is Definition) {
                return false
            }

            if (locale != null) {
                if (localeStrict && locale strictDoesntMatch signifier.locale) {
                    return false
                } else if (!localeStrict && locale doesntMatch signifier.locale) {
                    return false
                }
            }

            return true
        }
    }

}
