@file:Suppress("unused")

import rmg.apps.cards.base.SignifiedCriteria
import rmg.apps.cards.base.SignifierCriteria
import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Locale
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.WrittenWord

class WrittenWordByLocaleValueConverter {
    @JsName("toView")
    fun toView(signified: Signified, locale: Locale, strict: Boolean = false): String? {
        val criteria = SignifierCriteria.WrittenWordCriteria(locale = locale, localeStrict = strict)
        val signifier = signified.signifiers.filter { criteria.match(it) }.firstOrNull() as WrittenWord?

        return signifier?.word
    }
}

class DefinitionByLocaleValueConverter {
    @JsName("toView")
    fun toView(signified: Signified, locale: Locale, strict: Boolean = false): String? {
        val criteria = SignifierCriteria.DefinitionCriteria(locale = locale, localeStrict = strict)
        val signifier = signified.signifiers.filter { criteria.match(it) }.firstOrNull() as Definition?

        return signifier?.definition
    }
}

class AvailableLocaleWordFilterValueConverter {
    @JsName("toView")
    fun toView(locales: Array<Locale>, signified: Signified, strict: Boolean = false): Array<Locale> {
        return locales.filter {
            val criteria = SignifiedCriteria.ContainsSignifier(SignifierCriteria.WrittenWordCriteria(locale = it, localeStrict = strict))

            criteria.match(signified)
        }.toTypedArray()
    }
}

class LocaleDisplayNameValueConverter {

    companion object {
        // TODO(rmgrimm): Make this better, don't just hardcode names
        val names = mapOf(Locale(lang = "eng") to "English",
            Locale(lang = "kor") to "한국어",
            Locale(lang = "zho") to "中文",
            Locale(lang = "zho", script = "Hans") to "简体中文",
            Locale(lang = "zho", script = "Hant") to "繁体中文",
            Locale(lang = "zho", script = "Piny") to "拼音"
        )
    }

    @JsName("toView")
    fun toView(locale: Locale): String = names[locale] ?: "Unknown"
}
