package aurelia.framework

import aurelia.loader.Loader
import org.w3c.dom.Element
import kotlin.js.Promise

@JsModule("aurelia-framework")
external class Aurelia(
    loader: Loader? = definedExternally,
    container: Any? = definedExternally,
    resources: Any? = definedExternally
) {

    // TODO(rmgrimm): Finish fixing the types here
    var container: Any?
    val host: Element
    val loader: Loader
    var resources: Any?
    var use: FrameworkConfiguration

    fun enhance(bindingContext: Any = definedExternally, applicationHost: Any = definedExternally): Promise<Aurelia>
    fun setRoot(root: String = definedExternally, applicationHost: Any = definedExternally): Promise<Aurelia>
    fun start(): Promise<Aurelia>
}
