@file:Suppress("ArrayInDataClass")
package aurelia.pal

import org.w3c.dom.events.Event

/**
 * The expected callback signature for event handling functions
 */
typealias EventListenerCallback = (event: Event) -> dynamic

/**
 * Simple data class implementing [ModuleNameOptions]
 */
data class ModuleNameOptionsImpl(override var chunk: String?, override var exports: Array<String>?) : ModuleNameOptions
fun ModuleNameOptions(chunk: String? = null, exports: Array<String>? = null) = ModuleNameOptionsImpl(chunk, exports)
