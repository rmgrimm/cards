@file:Suppress("UNUSED_PARAMETER", "unused")
package rmg.support.kotlin.random

import kotlin.js.Math

class RandomNumberGeneratorJsImpl : RandomNumberGenerator {
    override fun generateFloat(min: Float, max: Float): Float = Math.random().toFloat() * (max - min) + min
    override fun generateDouble(min: Double, max: Double): Double = Math.random() * (max - min) + min
    override fun generateInt(min: Int, max: Int): Int = (Math.random() * (max - min) + min).toInt()
}
fun RandomNumberGenerator(seed: Long = 0): RandomNumberGenerator = RandomNumberGeneratorJsImpl()
