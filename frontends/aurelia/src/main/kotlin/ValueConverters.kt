@file:Suppress("unused")

import rmg.apps.cards.base.SignifiedCriteria
import rmg.apps.cards.base.SignifierCriteria
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier
import rmg.apps.cards.base.model.WrittenWord

typealias Locale = Signifier.Locale

class WrittenWordByLocaleValueConverter {
    @JsName("toView")
    fun toView(signified: Signified, locale: Locale): String? {
        val criteria = SignifierCriteria.WrittenWordCriteria(locale = locale)
        val signifier = signified.signifiers.filter(criteria::match).firstOrNull() as WrittenWord?

        return signifier?.word
    }
}

class AvailableLocaleWordFilterValueConverter {
    @JsName("toView")
    fun toView(locales: Array<Locale>, signified: Signified): Array<Locale> {
        return locales.filter {
            val criteria = SignifiedCriteria.ContainsSignifier(SignifierCriteria.WrittenWordCriteria(locale = it))

            criteria.match(signified)
        }.toTypedArray()
    }
}

class LocaleDisplayNameValueConverter {
    @JsName("toView")
    fun toView(locale: Locale) = when (locale) {
    // TODO(rmgrimm): Make this better, don't just hardcode names
        Locale(lang = "eng") -> "English"
        Locale(lang = "kor") -> "한국어"
        Locale(lang = "zho", script = "Hant") -> "繁体中文"
        Locale(lang = "zho", script = "Hans") -> "简体中文"
        Locale(lang = "zho", script = "Piny") -> "拼音"
        else -> "Unknown"
    }
}
