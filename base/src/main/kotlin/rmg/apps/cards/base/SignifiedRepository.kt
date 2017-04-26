package rmg.apps.cards.base

import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier

/**
 * Repository to hold [Signified]s and query against them
 */
interface SignifiedRepository<T> : MutableMap<T, Signified> {
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
     * @param randomized whether the order should be randomized
     * @param criteria a set of [SignifiedCriteria] describing which [Signified]s to return
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    fun find(maxResults: Int? = null, randomized: Boolean = false, criteria: SignifiedCriteria): List<Pair<T, Signified>>

    /**
     * Find matching [Signified] options using the builder/implicit receiver DSL
     *
     * @param maxResults the maximum number of results to return
     * @param randomized whether the order should be randomized
     * @param criteriaDsl a set of [DSL][SignifiedCriteria.AllBuilder] commands to build the criteria
     * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
     */
    fun findByDSL(maxResults: Int? = null, randomized: Boolean = false, criteriaDsl: SignifiedCriteria.AllBuilder.() -> Unit): List<Pair<T, Signified>> {
        val criteriaBuilder = SignifiedCriteria.AllBuilder()
        criteriaBuilder.criteriaDsl()

        return this.find(maxResults = maxResults, randomized = randomized, criteria = criteriaBuilder.criteria)
    }
}

/**
 * A sealed class of possible criteria for use with [SignifiedRepository.find].
 */
sealed class SignifiedCriteria {

    class AllBuilder {
        var criteria: SignifiedCriteria = Any

        fun type(type: Signified.Type) {
            criteria = criteria and SignifiedCriteria.Type(type)
        }

        fun writtenWord(lang: String? = null, country: String? = null, script: String? = null, weight: Int? = null) {
            var locale: Signifier.Locale? = null

            if (lang != null) {
                locale = Signifier.Locale(lang = lang, country = country, script = script)
            }

            criteria = criteria and WrittenWordSignifier(locale, weight)
        }
    }

    /**
     *  Criteria to select any signified
     */
    object Any : SignifiedCriteria()

    /**
     * Criteria to select based upon [Signified.type]
     */
    data class Type(val type: Signified.Type) : SignifiedCriteria()

    /**
     * Criteria to select [Signified] based upon the existence of contained [WrittenWordSignifier]
     *
     * Possible to further filter based [weight][WrittenWordSignifier.weight] or [locale][WrittenWordSignifier.locale].
     *
     * @param locale optional filter for the [Signifier.Locale] that causes a [WrittenWordSignifier] to match
     * @param weight optional filter for the weight that is allowed for the [WrittenWordSignifier] to match
     */
    data class WrittenWordSignifier(val locale: Signifier.Locale? = null, val weight: Int? = null) : SignifiedCriteria()

    data class DescriptionSignifier(val locale: Signifier.Locale? = null) : SignifiedCriteria()
    data class CompoundCriteria(val left: SignifiedCriteria, val right: SignifiedCriteria, val type: ConjunctionType) : SignifiedCriteria() {
        enum class ConjunctionType {
            AND, OR
        }
    }

    infix fun or(other: SignifiedCriteria): CompoundCriteria = CompoundCriteria(this, other, CompoundCriteria.ConjunctionType.OR)
    infix fun and(other: SignifiedCriteria): CompoundCriteria = CompoundCriteria(this, other, CompoundCriteria.ConjunctionType.AND)
}
