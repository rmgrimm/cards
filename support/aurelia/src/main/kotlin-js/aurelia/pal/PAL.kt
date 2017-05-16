@file:Suppress("unused")
@file:JsModule("aurelia-pal")
package aurelia.pal

import org.w3c.dom.Element
import org.w3c.dom.History
import org.w3c.dom.Location
import org.w3c.dom.svg.SVGElement
import org.w3c.performance.Performance
import org.w3c.xhr.XMLHttpRequest

external val DOM: Dom
external val FEATURE: Feature
external val PLATFORM: Platform

external interface Dom {
    val Element: JsClass<Element>
    val SVGElement: JsClass<SVGElement>
    val activeElement: Element
    val boundary: String
    var title: String
    // TODO(rmgrimm): Expand this as needed
}
external interface Feature {
    val htmlTemplateElement: Boolean
    val mutationObserver: Boolean
    val scopedCSS: Boolean
    val shadowDOM: Boolean
}
external interface ModuleNameOptions {
    var chunk: String?
    var exports: Array<String>?
}
external interface Platform {
    val Loader: Any
    val XMLHttpRequest: JsClass<XMLHttpRequest>
    val global: dynamic
    val history: History
    val location: Location
    val noop: (() -> Unit)
    val performance: Performance

    fun addEventListener(eventName: String, callback: EventListenerCallback, capture: Boolean = definedExternally)
    fun eachModule(callback: (moduleId: String, module: Any) -> Boolean)
    fun moduleName(moduleName: String, options: ModuleNameOptions = definedExternally): String
    fun moduleName(moduleName: String, chunk: String? = definedExternally): String
    fun removeEventListener(eventName: String, callback: EventListenerCallback, capture: Boolean = definedExternally)
    fun requestAnimationFrame(callback: (animationFrameStart: Double) -> Unit): Number
}

external fun AggregateError(message: String, innerError: Error = definedExternally, skipIfAlreadyAggregate: Boolean = definedExternally): Error
external fun initializePAL(callback: (platform: Platform, feature: Feature, dom: Dom) -> Unit)
external fun reset()
