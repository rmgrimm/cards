package rmg.apps.cards.base.dsl

import rmg.apps.cards.base.SignifiedCriteria
import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.SignifiedRepository.FindOrder
import rmg.apps.cards.base.SignifierCriteria
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier

/**
 * Find matching [Signified] options that match **all** of the supplied criteria
 *
 * This supports using the builder/implicit receiver [DSL][SignifiedCriteriaBuilder]
 *
 * @see SignifiedRepository.find
 *
 * @param T the type used as an ID for [Signified] objects held in the repository
 * @param U the type used as an ID for users, for purposes of spaced repetition
 * @param maxResults the maximum number of results to return
 * @param order how the signified should be ordered when fetching results
 * @param user the user ID to be used for [spaced repetition][FindOrder.SPACED_REPETITION] finds
 * @param allCriteria a set of [DSL][SignifiedCriteriaBuilder] commands to build the criteria
 * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
 */
inline fun <T, U> SignifiedRepository<T, U>.findByAll(maxResults: Int? = null,
                                                      order: FindOrder = FindOrder.NONE,
                                                      user: U? = null,
                                                      allCriteria: SignifiedCriteriaBuilder.All.() -> Unit): List<SignifiedRepository.StoredSignified<T>> {
    val criteriaBuilder = SignifiedCriteriaBuilder.All()
    criteriaBuilder.allCriteria()

    return this.find(maxResults = maxResults, order = order, user = user, criteria = criteriaBuilder.criteria)
}

/**
 * Find matching [Signified] options that match **any** of the supplied criteria
 *
 * This supports using the builder/implicit receiver [DSL][SignifiedCriteriaBuilder]
 *
 * @see SignifiedRepository.find
 *
 * @param T the type used as an ID for [Signified] objects held in the repository
 * @param U the type used as an ID for users, for purposes of spaced repetition
 * @param maxResults the maximum number of results to return
 * @param order how the signified should be ordered when fetching results
 * @param user the user ID to be used for [spaced repetition][FindOrder.SPACED_REPETITION] finds
 * @param anyCriteria a set of [DSL][SignifiedCriteriaBuilder] commands to build the criteria
 * @return a [List] of [Pair], with id as the left element and the [Signified] on the right
 */
inline fun <T, U> SignifiedRepository<T, U>.findByAny(maxResults: Int? = null,
                                                      order: FindOrder = FindOrder.NONE,
                                                      user: U? = null,
                                                      anyCriteria: SignifiedCriteriaBuilder.Any.() -> Unit): List<SignifiedRepository.StoredSignified<T>> {
    val criteriaBuilder = SignifiedCriteriaBuilder.Any()
    criteriaBuilder.anyCriteria()

    return this.find(maxResults = maxResults, order = order, user = user, criteria = criteriaBuilder.criteria)
}

@DslMarker
annotation class SignifiedCriteriaDsl

@SignifiedCriteriaDsl
sealed class SignifiedCriteriaBuilder(
    val startingCriteria: SignifiedCriteria,
    val conjunctionType: SignifiedCriteria.CompoundCriteria.ConjunctionType) {

    var criteria: SignifiedCriteria = startingCriteria

    class All : SignifiedCriteriaBuilder(
        startingCriteria = SignifiedCriteria.Any,
        conjunctionType = SignifiedCriteria.CompoundCriteria.ConjunctionType.AND
    )

    class Any : SignifiedCriteriaBuilder(
        startingCriteria = SignifiedCriteria.None,
        conjunctionType = SignifiedCriteria.CompoundCriteria.ConjunctionType.OR
    )

    fun addCriteria(criteria: SignifiedCriteria) {
        if (this.criteria == startingCriteria) {
            this.criteria = criteria
        } else {
            this.criteria = SignifiedCriteria.CompoundCriteria(this.criteria, criteria, conjunctionType)
        }
    }

    fun equalTo(signified: Signified) {
        addCriteria(SignifiedCriteria.EqualTo(signified))
    }

    fun matches(criteria: SignifiedCriteria) {
        addCriteria(criteria)
    }

    inline fun not(build: All.() -> Unit) {
        val compoundBuilder = All()
        compoundBuilder.build()

        addCriteria(SignifiedCriteria.Not(compoundBuilder.criteria))
    }

    inline fun all(build: All.() -> Unit) {
        val compoundBuilder = All()
        compoundBuilder.build()

        addCriteria(compoundBuilder.criteria)
    }

    inline fun any(build: Any.() -> Unit) {
        val compoundBuilder = Any()
        compoundBuilder.build()

        addCriteria(compoundBuilder.criteria)
    }

    fun type(type: Signified.Type) {
        addCriteria(SignifiedCriteria.Type(type))
    }

    fun contains(signifierCriteria: SignifierCriteria) {
        addCriteria(SignifiedCriteria.ContainsSignifier(signifierCriteria))
    }

    fun hasWrittenWord(locale: Signifier.Locale? = null, weight: Int? = null) {
        addCriteria(SignifiedCriteria.ContainsSignifier(SignifierCriteria.WrittenWordCriteria(locale, weight)))
    }

    fun hasWrittenWord(lang: String, country: String? = null, script: String? = null, weight: Int? = null) {
        val locale = Signifier.Locale(lang = lang, country = country, script = script)

        addCriteria(SignifiedCriteria.ContainsSignifier(SignifierCriteria.WrittenWordCriteria(locale, weight)))
    }

    fun hasDefinition(locale: Signifier.Locale? = null) {
        addCriteria(SignifiedCriteria.ContainsSignifier(SignifierCriteria.DefinitionCriteria(locale)))
    }

    fun hasDefinition(lang: String, country: String? = null, script: String? = null) {
        val locale = Signifier.Locale(lang = lang, country = country, script = script)

        addCriteria(SignifiedCriteria.ContainsSignifier(SignifierCriteria.DefinitionCriteria(locale)))
    }
}
