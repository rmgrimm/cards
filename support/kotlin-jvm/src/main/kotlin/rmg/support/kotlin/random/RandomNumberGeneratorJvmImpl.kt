package rmg.support.kotlin.random

import java.util.*

class RandomNumberGeneratorJvmImpl(seed: Long) : RandomNumberGenerator {

    val javaRandom = Random(seed)

    override fun generateFloat(min: Float, max: Float): Float = javaRandom.nextFloat() * (max - min) + min
    override fun generateDouble(min: Double, max: Double): Double = javaRandom.nextDouble() * (max - min) + min
    override fun generateInt(min: Int, max: Int): Int = if (max - min > 0) {
        javaRandom.nextInt(max - min) + min
    } else {
        min
    }
}

fun RandomNumberGenerator(seed: Long = System.currentTimeMillis()): RandomNumberGenerator = RandomNumberGeneratorJvmImpl(seed = seed)
