package aurelia.pal

import org.w3c.dom.Element
import org.w3c.dom.svg.SVGElement

@JsModule("aurelia-pal")
external interface Dom {
    val Element: JsClass<Element>
    val SVGElement: JsClass<SVGElement>
    val activeElement: Element
    val boundary: String
    var title: String
    // TODO(rmgrimm): Expand this as needed
}
