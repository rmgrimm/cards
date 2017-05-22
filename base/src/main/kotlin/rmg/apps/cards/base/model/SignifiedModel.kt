package rmg.apps.cards.base.model

import kotlin.reflect.KClass

/**
 * A thing or idea that is "signified" by "[signifiers][Signifier]".
 */
data class Signified(val type: Type, val signifiers: List<Signifier>) : List<Signifier> by signifiers {
    enum class Type {
        DETERMINER, PRONOUN, NOUN, NUMERAL, MEASURE_WORD, VERB, ADJECTIVE, ADVERB, EXCLAMATION,
    }
}

/**
 * A word/definition/etc that signifies [something or some idea][Signified].
 */
sealed class Signifier: SignifierTypeIndicator {
    override final val type: SignifierType
        get() = this::class
}
typealias SignifierType = KClass<out Signifier>

/**
 * A signifier that can be localized
 */
interface LocalizedSignifier: SignifierTypeIndicator {
    val locale: Locale
}

/**
 * This interface is for convenience for retrieving signifier type
 */
interface SignifierTypeIndicator {
    val type: SignifierType
}

/**
 * A written word, which [signifies][Signifier] a [thing or idea][Signified]
 */
data class WrittenWord(override val locale: Locale, val word: String, val weight: Int = word.length) : Signifier(), LocalizedSignifier {
    constructor(lang: String, country: String? = null, script: String? = null, word: String, weight: Int = word.length) : this(Locale(lang, country, script), word, weight)

    companion object: SignifierTypeIndicator {
        override val type: SignifierType = WrittenWord::class
    }
}

/**
 * A written definition, which describes
 */
data class Definition(override val locale: Locale, val definition: String) : Signifier(), LocalizedSignifier {
    constructor(lang: String, country: String? = null, script: String? = null, definition: String) : this(Locale(lang, country, script), definition)

    companion object: SignifierTypeIndicator {
        override val type: SignifierType = Definition::class
    }
}

data class SpokenWord(override val locale: Locale, val data: Any) : Signifier(), LocalizedSignifier {
    init {
        TODO("Figure out how to handle sound data")
    }

    companion object: SignifierTypeIndicator {
        override val type: SignifierType = SpokenWord::class
    }
}

data class Image(val data: Any) : Signifier() {
    init {
        TODO("Figure out how to handle visual data")
    }

    companion object: SignifierTypeIndicator {
        override val type: SignifierType = Image::class
    }
}
