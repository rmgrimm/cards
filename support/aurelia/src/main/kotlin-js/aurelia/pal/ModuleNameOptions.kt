package aurelia.pal

@JsModule("aurelia-pal")
external interface ModuleNameOptions {
    var chunk: String?
    var exports: Array<String>?
}

/**
 * Simple data class implementing [ModuleNameOptions]
 */
data class ModuleNameOptionsImpl(override var chunk: String?, @Suppress("ArrayInDataClass") override var exports: Array<String>?) : ModuleNameOptions
fun ModuleNameOptions(chunk: String? = null, exports: Array<String>? = null) = ModuleNameOptionsImpl(chunk, exports)
