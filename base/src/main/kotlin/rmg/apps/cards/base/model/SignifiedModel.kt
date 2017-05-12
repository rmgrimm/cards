package rmg.apps.cards.base.model

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
sealed class Signifier

/**
 * A written word, which [signifies][Signifier] a [thing or idea][Signified]
 */
data class WrittenWord(val locale: Locale, val word: String, val weight: Int = word.length) : Signifier() {
    constructor(lang: String, country: String? = null, script: String? = null, word: String, weight: Int = word.length) : this(Locale(lang, country, script), word, weight)
}

/**
 * A written definition, which describes
 */
data class Definition(val locale: Locale, val definition: String) : Signifier()

data class SpokenWord(val locale: Locale, val data: Any) : Signifier() {
    init {
        TODO("Figure out how to handle sound data")
    }
}

data class Image(val data: Any) : Signifier() {
    init {
        TODO("Figure out how to handle visual data")
    }
}
