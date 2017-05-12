package rmg.apps.cards.base.dsl

import rmg.apps.cards.base.MutableSignifiedRepository
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier
import rmg.apps.cards.base.model.WrittenWord

fun <T, U> MutableSignifiedRepository<T, U>.add(element: SignifiedBuilder.() -> Unit): T {
    val builder = SignifiedBuilder()
    builder.element()

    return this.add(builder.signified)
}

fun <T, U> MutableSignifiedRepository<T, U>.addAll(elements: SignifiedListBuilder.() -> Unit) {
    val builder = SignifiedListBuilder()
    builder.elements()

    return this.addAll(builder.signifieds)
}

@DslMarker
annotation class SignifiedBuilderDslMarker

@SignifiedBuilderDslMarker
class SignifiedBuilder {

    lateinit var type: Signified.Type
    val signifiers = ArrayList<Signifier>()

    val signified: Signified
        get() = Signified(type = type, signifiers = signifiers)

    fun type(type: Signified.Type) {
        this.type = type
    }

    fun writtenWord(lang: String, country: String? = null, script: String? = null, word: String, weight: Int = word.length) {
        signifiers.add(WrittenWord(lang = lang, country = country, script = script, word = word, weight = weight))
    }

    // TODO(rgrimm): Build the functions for other signifier types
}

@SignifiedBuilderDslMarker
class SignifiedListBuilder {

    val signifieds = ArrayList<Signified>()

    fun signified(build: SignifiedBuilder.() -> Unit) {
        val signifiedBuilder = SignifiedBuilder()
        signifiedBuilder.build()

        signifieds.add(signifiedBuilder.signified)
    }
}
