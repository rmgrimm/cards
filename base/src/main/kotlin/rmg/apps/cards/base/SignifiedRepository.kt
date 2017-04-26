package rmg.apps.cards.base

import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier

/**
 * Repository to hold [Signified\s and query against them
 */
interface SignifiedRepository<T>: MutableMap<T, Signified> {
    /**
     * Add a new [Signified]
     *
     * @param element the new [Signified] to add
     * @return the ID of the new entry
     */
    fun add(elements: Signified): T

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
     * @param randomized whether the order should be randomized
     * @param criteria a set of [SignifiedCriteria] describing which [Signified]s to return
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    fun find(maxResults: Int? = null, randomized: Boolean = false, criteria: SignifiedCriteria): List<Pair<T, Signified>>
}

/**
 * A sealed class of possible criteria for use with [SignifiedRepository.find].
 */
sealed class SignifiedCriteria {
    /**
     * Criteria to select based upon [Signified.type]
     */
    data class Type(val type: Signified.Type) : SignifiedCriteria()

    /**
     * Criteria to select [Signified] based upon the existence of a [WrittenWordSignifier]
     *
     * Possible to further filter based on weight or locale of the [WrittenWordSignifier].
     *
     * @param locale optional filter for the [Signifier.Locale] that causes a [WrittenWordSignifier] to match
     * @param weight optional filter for the weight that is allowed for the [WrittenWordSignifier] to match
     */
    data class WrittenWordSignifier(val locale: Signifier.Locale?, val weight: Int?)
    data class DescriptionSignifier(val locale: Signifier.Locale?)
    data class CompoundCriteria(val left: SignifiedCriteria, val right: SignifiedCriteria, val type: ConjunctionType) {
        enum class ConjunctionType {
            AND, OR
        }
    }

    infix fun or(other: SignifiedCriteria): CompoundCriteria = CompoundCriteria(this, other, CompoundCriteria.ConjunctionType.OR)
    infix fun and(other: SignifiedCriteria): CompoundCriteria = CompoundCriteria(this, other, CompoundCriteria.ConjunctionType.AND)
}
