package aurelia.binding

@JsModule("aurelia-binding")
external interface CollectionObserver {
    fun subscribe(callback: (splices: dynamic) -> Unit): Disposable
}
