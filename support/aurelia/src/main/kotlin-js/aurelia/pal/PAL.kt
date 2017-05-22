@file:Suppress("unused")
@file:JsModule("aurelia-pal")
package aurelia.pal

external val DOM: Dom
external val FEATURE: Feature
external val PLATFORM: Platform

external fun AggregateError(message: String, innerError: Error = definedExternally, skipIfAlreadyAggregate: Boolean = definedExternally): Error
external fun initializePAL(callback: (platform: Platform, feature: Feature, dom: Dom) -> Unit)
external fun reset()
