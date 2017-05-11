import rmg.apps.cards.backend.InMemoryQuiz

@JsName("RmgQuiz")
class Quiz {
    companion object {
        fun generate(): InMemoryQuiz {
            return InMemoryQuiz(emptyList())
        }
    }
}
