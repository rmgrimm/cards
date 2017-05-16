@file:Suppress("unused")

import rmg.apps.cards.backend.InMemorySignifiedRepository
import rmg.apps.cards.base.model.Signified

@JsName("signifiedRepository")
val signifiedRepository = InMemorySignifiedRepository {
    signified {
        type(Signified.Type.VERB)
        writtenWord(lang = "zho", script = "Hans", word = "爱")
        writtenWord(lang = "zho", script = "Hant", word = "愛")
        writtenWord(lang = "zho", script = "Piny", word = "ai4", weight = 1)
        writtenWord(lang = "eng", word = "to love")
    }
    signified {
        type(Signified.Type.NUMERAL)
        writtenWord(lang = "zho", word = "八")
        writtenWord(lang = "zho", script = "Piny", word = "ba1", weight = 1)
        writtenWord(lang = "eng", word = "eight")
    }
    signified {
        type(Signified.Type.NOUN)
        writtenWord(lang = "zho", word = "爸爸")
        writtenWord(lang = "zho", script = "Piny", word = "ba4ba5", weight = 2)
        writtenWord(lang = "eng", word = "father")
    }
    signified {
        type(Signified.Type.NOUN)
        writtenWord(lang = "eng", word = "Test")
        definition(lang = "eng", definition = "a procedure intended to establish the quality, performance, or reliability of something, especially before it is taken into widespread use")
    }
}
