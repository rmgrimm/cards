package rmg.apps.cards.backend

import rmg.apps.cards.base.MutableSignifiedRepository
import rmg.apps.cards.base.PagedArray
import rmg.apps.cards.base.SignifiedCriteria
import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.dsl.SignifiedListBuilder
import rmg.apps.cards.base.dsl.addAll
import rmg.apps.cards.base.model.Definition
import rmg.apps.cards.base.model.Signified
import rmg.apps.cards.base.model.Signifier
import rmg.apps.cards.base.model.WrittenWord

/**
 * [MutableSignifiedRepository] that holds all data in memory in an [ArrayList]
 *
 * IDs of [Signified] will be of type [Int], to match the position within the array list.
 *
 * This will only support one user, so the user ID type is [Unit]
 */
class InMemorySignifiedRepository constructor() : MutableSignifiedRepository<Int, Unit> {
    constructor(elements: SignifiedListBuilder.() -> Unit): this() {
        addAll(elements)
    }

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
    override fun putAll(from: Map<out Int, Signified>) = from.forEach { (key, value) -> internalPut(key, value) }
    override fun remove(key: Int): Signified? = internalPut(key, null)

    override val locales: Set<Signifier.Locale>
        get() {
            val output = HashSet<Signifier.Locale>()
            backingList.forEach {
                it?.signifiers?.forEach {
                    when(it) {
                        is WrittenWord -> output.add(it.locale)
                        is Definition -> output.add(it.locale)
                    }
                }
            }

            return output
        }

    override fun find(maxResults: Int?,
                      order: SignifiedRepository.FindOrder,
                      user: Unit?,
                      criteria: SignifiedCriteria): List<SignifiedRepository.StoredSignified<Int>> {
        val resultList = ArrayList<SignifiedRepository.StoredSignified<Int>>(maxResults ?: backingList.size)

        // TODO(rmgrimm): Handle the order parameter properly

        for ((key, value) in entries) {
            if (criteria.match(value)) {
                resultList.add(SignifiedRepository.StoredSignified(key, value))
                if (resultList.size == maxResults) {
                    break
                }
            }
        }

        return resultList
    }

    override fun findPagedArray(resultsPerPage: Int,
                                maxResults: Int?,
                                order: SignifiedRepository.FindOrder,
                                user: Unit?,
                                criteria: SignifiedCriteria): PagedArray<SignifiedRepository.StoredSignified<Int>> {
        val results = find(maxResults = maxResults,
            order = order,
            user = user,
            criteria = criteria)

        return InMemoryPagedArray(0, resultsPerPage, results.toTypedArray())
    }



}
