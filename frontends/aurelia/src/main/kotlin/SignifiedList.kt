import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Locale
import rmg.apps.cards.base.model.Signified

interface SignifiedListSettings {
    var displayWordLocales: Array<Locale>?
    var displayDefinitionLocale: Locale?
}

class SignifiedListViewModel(
    val repository: SignifiedRepository<*, *>,
    val settings: SignifiedListSettings
): SignifiedListSettings by settings {

    init {
        if (displayWordLocales === undefined) {
            displayWordLocales = availableWordLocales
        }
        if (displayDefinitionLocale === undefined) {
            displayDefinitionLocale = availableDefinitionLocales.first()
        }
    }

    val availableWordLocales: Array<Locale>
        get() = repository.writtenWordLocales.toTypedArray()
    val availableDefinitionLocales: Array<Locale>
        get() = repository.definitionLocales.toTypedArray()

    val currentPage = repository.findPagedArray(resultsPerPage = 10)

}

class SignifiedListDetailViewModel {

    var id: Any? = null
    lateinit var signified: Signified

    @JsName("hasDefinition")
    fun hasDefinition(locale: Locale?): Boolean = if (locale == null) {
        false
    } else {
        signified.any { signifier ->
            signifier is Definition && signifier.locale matches locale
        }
    }

    @JsName("activate")
    fun activate(model: SignifiedRepository.StoredSignified<*>) {
        id = model.id
        signified = model.signified
    }

}
