package rmg.apps.cards.base.model

data class Signified(val type: Type, val signifiers: List<Signifier>) : List<Signifier> by signifiers {
    enum class Type {
        NOUN, VERB, ADJECTIVE, ADVERB, EXCLAMATION,
    }
}

sealed class Signifier {
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
    }
}

data class WrittenWord(val locale: Locale, val word: String, val weight: Int?) : Signifier() {
    constructor(locale: Locale, word: String) : this(locale, word, word.length)
}

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
