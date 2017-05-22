package aurelia.pal

@JsModule("aurelia-pal")
external interface Feature {
    val htmlTemplateElement: Boolean
    val mutationObserver: Boolean
    val scopedCSS: Boolean
    val shadowDOM: Boolean
}
