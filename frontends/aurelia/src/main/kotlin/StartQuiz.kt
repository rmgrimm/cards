import aurelia.binding.BindingEngine
import aurelia.binding.Disposable
import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Locale
import rmg.apps.cards.base.model.SignifierType
import rmg.apps.cards.base.model.WrittenWord

interface StartQuizSettings {
    var questionSignifierTypes: Array<SignifierType>?
    var questionLocales: Array<Locale>?
    var answerSignifierTypes: Array<SignifierType>?
    var answerLocales: Array<Locale>?
    var sameSignifiedType: Boolean?
    var sameWeight: Boolean?
}

class StartQuizViewModel(
    val repository: SignifiedRepository<*, *>,
    val settings: StartQuizSettings,
    bindingEngine: BindingEngine
) : StartQuizSettings by settings {

    var questionTypeSubsription: Disposable
    var answerTypeSubscription: Disposable

    init {
        if (questionSignifierTypes === undefined) {
            questionSignifierTypes = emptyArray()
        }
        if (questionLocales === undefined) {
            questionLocales = emptyArray()
        }
        if (answerSignifierTypes === undefined) {
            answerSignifierTypes = emptyArray()
        }
        if (answerLocales === undefined) {
            answerLocales = emptyArray()
        }
        if (sameSignifiedType === undefined) {
            sameSignifiedType = true
        }
        if (sameWeight === undefined) {
            sameWeight = true
        }

        questionTypeSubsription = bindingEngine.collectionObserver(questionSignifierTypes!!).subscribe {
            questionLocales = emptyArray()
        }
        answerTypeSubscription = bindingEngine.collectionObserver(answerSignifierTypes!!).subscribe {
            answerLocales = emptyArray()
        }
    }

    val availableSignifierTypes: Array<SignifierType> = arrayOf(
        WrittenWord.type,
        Definition.type
    )

    val availableQuestionLocales: Array<Locale>
        get() = questionSignifierTypes.orEmpty().flatMap {
            repository.localesBySignifierType[it] ?: emptySet()
        }.toTypedArray()

    val availableAnswerLocales: Array<Locale>
        get() = answerSignifierTypes.orEmpty().flatMap {
            repository.localesBySignifierType[it] ?: emptySet()
        }.toTypedArray()

}
