import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Locale
import rmg.apps.cards.base.model.Signified

class SignifiedListViewModel(val repository: SignifiedRepository<*, *>) {

    val availableWordLocales: Array<Locale>
        get() = repository.writtenWordLocales.toTypedArray()
    val availableDefinitionLocales: Array<Locale>
        get() = repository.definitionLocales.toTypedArray()

    var displayWordLocales: Array<Locale> = availableWordLocales
    var displayDefinitionLocale: Locale = availableDefinitionLocales.first()

    val currentPage = repository.findPagedArray(resultsPerPage = 10)

}

class SignifiedListDetailViewModel {

    var id: Any? = null
    lateinit var signified: Signified

    @JsName("hasDefinition")
    fun hasDefinition(locale: Locale?): Boolean = signified.any { signifier ->
        locale != null && signifier is Definition && signifier.locale matches locale
    }

    @JsName("activate")
    fun activate(model: SignifiedRepository.StoredSignified<*>) {
        id = model.id
        signified = model.signified
    }

}
