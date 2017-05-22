package aurelia.templating.resources

@JsModule("aurelia-templating-resources")
external class BindingSignaler {
    var signals: Any

    fun signal(signal: String): Unit
}
