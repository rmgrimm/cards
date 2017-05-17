package rmg.support.kotlin.random

interface RandomNumberGenerator {
    fun generateFloat(min: Float = 0f, max: Float = 1f): Float
    fun generateDouble(min: Double = 0.0, max: Double = 1.0): Double
    fun generateInt(min: Int, max: Int): Int
}
