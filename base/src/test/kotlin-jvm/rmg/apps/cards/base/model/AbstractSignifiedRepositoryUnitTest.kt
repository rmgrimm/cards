package rmg.apps.cards.base.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import rmg.apps.cards.base.SignifiedRepository

/**
 * Abstract test to facilitate correctness checking of [SignifiedRepository] subclasses
 *
 * @param I the class used to identify signified objects
 * @param R the repository class
 */
abstract class AbstractSignifiedRepositoryUnitTest<I, R : SignifiedRepository<I>> {

    lateinit var repository: R

    private fun generateEmptySignified(type: Signified.Type = Signified.Type.NOUN) = Signified(type, emptyList())

    /**
     * Generator for a working instance of the repository
     *
     * @return a usable instance of the [SignifiedRepository] subclass
     */
    abstract fun createInstance(): R

    /**
     * Generator to return the would-be first ID of a signified
     * contained within the repository
     *
     * @return the first ID
     */
    abstract fun getFirstId(): I

    @Before
    fun setUp() {
        repository = createInstance()
    }

    @Test
    fun testAdd_IdReturned() {
        val id = repository.add(generateEmptySignified())

        assertEquals(getFirstId(), id)
    }

    @Test
    fun testAddAll_EmptySuccess() {
        repository.addAll(emptyList())

        // No exceptions
    }

    @Test
    fun testAddAll_SingleSuccess() {
        repository.addAll(listOf(generateEmptySignified()))

        assertEquals(1, repository.size)
    }

    @Test
    fun testAddAll_MultipleSuccess() {
        repository.addAll(listOf(generateEmptySignified(), generateEmptySignified()))

        assertEquals(2, repository.size)
    }

    @Test
    fun testSize_Nothing() {
        assertEquals(0, repository.size)
    }

    @Test
    fun testSize_Single() {
        repository.add(generateEmptySignified())

        assertEquals(1, repository.size)
    }

    @Test
    fun testContainsKey_DoesntExist() {
        assertFalse(repository.contains(getFirstId()))
    }

    @Test
    fun testContainsKey_Exists() {
        repository.add(generateEmptySignified())

        assertTrue(repository.contains(getFirstId()))
    }

    @Test
    fun testContainsValue_Contains() {
        val signified = generateEmptySignified()

        repository.add(signified)

        assertTrue(repository.containsValue(signified))
    }

    @Test
    fun testContainsValue_DoesntContain() {
        val signified = generateEmptySignified()

        assertFalse(repository.containsValue(signified))
    }

    @Test
    fun testGet_Success() {
        repository.add(generateEmptySignified())
        val signified = generateEmptySignified()
        val id = repository.add(signified)
        repository.add(generateEmptySignified())

        assertSame(signified, repository[id])
    }

    @Test
    fun testIsEmpty_WhenEmpty() {
        assertTrue(repository.isEmpty())
    }

    @Test
    fun testIsEmpty_WhenNotEmpty() {
        repository.add(generateEmptySignified())

        assertFalse(repository.isEmpty())
    }

    private fun addSignifiedsWithHoles(): I {
        val removedId: I = repository.add(generateEmptySignified(Signified.Type.ADJECTIVE))
        repository.addAll(
            listOf(
                generateEmptySignified(Signified.Type.NOUN),
                generateEmptySignified(Signified.Type.VERB),
                generateEmptySignified(Signified.Type.VERB)
            ))
        repository.remove(removedId)

        return removedId
    }

    @Test
    fun testGetEntries_SuccessMultipleWitHoles() {
        addSignifiedsWithHoles()

        val entries = repository.entries

        assertEquals(3, entries.size)
        assertEquals(0, entries.filter { it.value.type == Signified.Type.ADJECTIVE }.size)
        assertEquals(1, entries.filter { it.value.type == Signified.Type.NOUN }.size)
        assertEquals(2, entries.filter { it.value.type == Signified.Type.VERB }.size)
    }

    @Test
    fun testGetKeys_SuccessWithHoles() {
        val removedId = addSignifiedsWithHoles()

        val keys = repository.keys

        assertEquals(3, keys.size)
        assertFalse(keys.contains(removedId))
    }

    @Test
    fun testGetValues_SuccessWithHoles() {
        addSignifiedsWithHoles()

        val entries = repository.values

        assertEquals(3, entries.size)
        assertEquals(0, entries.filter { it.type == Signified.Type.ADJECTIVE }.size)
        assertEquals(1, entries.filter { it.type == Signified.Type.NOUN }.size)
        assertEquals(2, entries.filter { it.type == Signified.Type.VERB }.size)
    }

    @Test
    fun testClear_Success() {
        repository.addAll(listOf(generateEmptySignified(), generateEmptySignified()))

        repository.clear()

        assertEquals(0, repository.size)
    }

    @Test
    @Ignore
    fun put() {
    }

    @Test
    @Ignore
    fun putAll() {
    }

    @Test
    @Ignore("Remove has been tested by now")
    fun remove() {
    }

    @Test
    @Ignore
    fun find() {
    }

}
