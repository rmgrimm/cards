package rmg.apps.cards.backend

import rmg.apps.cards.base.SignifiedCriteria
import rmg.apps.cards.base.MutableSignifiedRepository
import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.model.Signified

/**
 * [MutableSignifiedRepository] that holds all data in memory in an [ArrayList]
 *
 * IDs of [Signified] will be of type [Int], to match the position within the array list.
 *
 * This will only support one user, so the user ID type is [Unit]
 */
class InMemorySignifiedRepository : MutableSignifiedRepository<Int, Unit> {
    data class Entry(override val key: Int, override var value: Signified) : MutableMap.MutableEntry<Int, Signified> {
        override fun setValue(newValue: Signified): Signified {
            value = newValue
            return value
        }
    }

    private val backingList = ArrayList<Signified?>()

    override fun add(element: Signified): Int {
        backingList.add(element)
        return backingList.lastIndex
    }

    override fun addAll(elements: Iterable<Signified>) {
        elements.forEach { add(it) }
    }

    override val size: Int get() = keys.size
    override fun containsKey(key: Int): Boolean = keys.contains(key)
    override fun containsValue(value: Signified): Boolean = backingList.contains(value)
    override fun get(key: Int): Signified? = backingList[key]
    override fun isEmpty(): Boolean = backingList.isEmpty()
    override val entries: MutableSet<MutableMap.MutableEntry<Int, Signified>>
        get() = HashSet(keys.map { Entry(it, (backingList[it])!!) })
    override val keys: MutableSet<Int>
        get() = HashSet((0..backingList.lastIndex).filter { backingList[it] != null })
    override val values: MutableCollection<Signified>
        get() = ArrayList(backingList.filterNotNull())

    override fun clear() = backingList.clear()

    private fun internalPut(key: Int, value: Signified?): Signified? {
        while (backingList.lastIndex < key) {
            backingList.add(null)
        }

        val output = backingList[key]
        backingList[key] = value

        return output
    }

    override fun put(key: Int, value: Signified): Signified? = internalPut(key, value)
    override fun putAll(from: Map<out Int, Signified>) = from.forEach { internalPut(it.key, it.value) }
    override fun remove(key: Int): Signified? = internalPut(key, null)

    override fun find(maxResults: Int?, order: SignifiedRepository.FindOrder, user: Unit?, criteria: SignifiedCriteria): List<Pair<Int, Signified>> {
        val resultList = ArrayList<Pair<Int, Signified>>(maxResults ?: backingList.size)

        // TODO(rmgrimm): Handle the order parameter properly

        for (entry in entries) {
            if (criteria.match(entry.value)) {
                resultList.add(entry.toPair())
                if (resultList.size == maxResults) {
                    break
                }
            }
        }

        return resultList
    }

}
