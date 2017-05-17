package rmg.apps.cards.base

import rmg.support.kotlin.random.RandomNumberGenerator

inline fun <reified T> Iterable<T>.toRandomizedArray(randomNumberGenerator: RandomNumberGenerator = RandomNumberGenerator()): Array<T> {
    val list = this.toMutableList()

    return Array(list.size) {
        list.removeAt(randomNumberGenerator.generateInt(0, list.lastIndex))
    }
}

