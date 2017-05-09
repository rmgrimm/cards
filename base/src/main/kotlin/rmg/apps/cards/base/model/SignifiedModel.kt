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
sealed class Signifier {
    /**
     * The Locale of a [word or definition][Signifier]
     */
    data class Locale(val lang: String, val country: String? = null, val script: String? = null) {
        infix fun matches(that: Locale): Boolean {
            if (this.lang != that.lang) {
                return false
            }

            if (this.country != null && that.country != null && this.country != that.country) {
                return false
            }

            if (this.script != null && that.script != null && this.script != that.script) {
                return false
            }

            return true
        }

        infix fun doesntMatch(that: Locale): Boolean {
            return !(this matches that)
        }
    }
}

/**
 * A written word, which [signifies][Signifier] a [thing or idea][Signified]
 */
data class WrittenWord(val locale: Locale, val word: String, val weight: Int = word.length) : Signifier() {
    constructor(lang: String, country: String? = null, script: String? = null, word: String, weight: Int = word.length) : this(Signifier.Locale(lang, country, script), word, weight)
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
