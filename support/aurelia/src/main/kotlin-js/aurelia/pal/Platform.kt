package aurelia.pal

import org.w3c.dom.History
import org.w3c.dom.Location
import org.w3c.performance.Performance
import org.w3c.xhr.XMLHttpRequest

@JsModule("aurelia-pal")
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
