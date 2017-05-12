package rmg.apps.cards.base.model

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import rmg.apps.cards.base.MutableSignifiedRepository
import rmg.apps.cards.base.dsl.addAll
import rmg.apps.cards.base.dsl.findByAll
import rmg.apps.cards.base.dsl.findByAny
import kotlin.test.*

/**
 * Abstract test to facilitate correctness checking of [MutableSignifiedRepository] subclasses
 *
 * @param I the class used to identify signified objects
 * @param U the class used to identify users
 * @param R the repository class
 */
@Ignore("Abstract test to be used as base for other tests")
abstract class AbstractMutableSignifiedRepositoryUnitTest<I, U, R : MutableSignifiedRepository<I, U>> {

    /**
     * Generator for a working instance of the repository
     *
     * @return a usable instance of the [MutableSignifiedRepository] subclass
     */
    abstract fun createInstance(): R

    /**
     * Generator to return the would-be first ID of a signified
     * contained within the repository
     *
     * @return the first ID
     */
    abstract fun getFirstId(): I

    /**
     * Generator to return the would-be user for retrieving ordered [Signified]
     *
     * @return a sample User ID
     */
    abstract fun getUserId(): U

    /**
     * The repository that is being tested
     */
    lateinit var repository: R

    private fun generateEmptySignified(type: Signified.Type = Signified.Type.NOUN) = Signified(type, emptyList())

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

    private fun addSignifiedForFindTests() {
        repository.addAll {
            signified {
                type(Signified.Type.VERB)
                writtenWord(lang = "zho", script = "Hans", word = "爱")
                writtenWord(lang = "zho", script = "Hant", word = "愛")
                writtenWord(lang = "zho", script = "Piny", word = "ai4", weight = 1)
                writtenWord(lang = "eng", word = "to love")
            }
            signified {
                type(Signified.Type.NUMERAL)
                writtenWord(lang = "zho", script = "Hans", word = "八")
                writtenWord(lang = "zho", script = "Hant", word = "八")
                writtenWord(lang = "zho", script = "Piny", word = "ba1", weight = 1)
                writtenWord(lang = "eng", word = "eight")
            }
            signified {
                type(Signified.Type.NOUN)
                writtenWord(lang = "zho", script = "Hans", word = "爸爸")
                writtenWord(lang = "zho", script = "Hant", word = "爸爸")
                writtenWord(lang = "zho", script = "Piny", word = "ba4ba5", weight = 2)
                writtenWord(lang = "eng", word = "father")
            }
            signified {
                type(Signified.Type.NOUN)
                writtenWord(lang = "zho", script = "Hans", word = "杯子")
                writtenWord(lang = "zho", script = "Hant", word = "杯子")
                writtenWord(lang = "zho", script = "Piny", word = "bei1zi5", weight = 2)
                writtenWord(lang = "eng", word = "cup")
            }
            signified {
                type(Signified.Type.NOUN)
                writtenWord(lang = "zho", script = "Hans", word = "北京")
                writtenWord(lang = "zho", script = "Hant", word = "北京")
                writtenWord(lang = "zho", script = "Piny", word = "bei3jing1", weight = 2)
                writtenWord(lang = "eng", word = "Beijing")
            }
            signified {
                type(Signified.Type.MEASURE_WORD)
                writtenWord(lang = "zho", script = "Hans", word = "本")
                writtenWord(lang = "zho", script = "Hant", word = "本")
                writtenWord(lang = "zho", script = "Piny", word = "ben3", weight = 1)
                writtenWord(lang = "eng", word = "measure word for books")
            }

                // A few other words
            signified {
                type(Signified.Type.PRONOUN)
                writtenWord(lang = "eng", word = "I")
                writtenWord(lang = "eng", word = "me")
                writtenWord(lang = "zho", script = "Piny", word = "wo3", weight = 1)
            }
            signified {
                type(Signified.Type.DETERMINER)
                writtenWord(lang = "eng", word = "My")
                writtenWord(lang = "zho", script = "Piny", word = "wo3de5", weight = 2)
            }
            signified {
                type(Signified.Type.PRONOUN)
                writtenWord(lang = "eng", word = "You")
                writtenWord(lang = "zho", script = "Piny", word = "ni3", weight = 1)
            }
            signified {
                type(Signified.Type.DETERMINER)
                writtenWord(lang = "eng", word = "Your")
                writtenWord(lang = "zho", script = "Piny", word = "ni3de5", weight = 2)
            }
            signified {
                type(Signified.Type.PRONOUN)
                writtenWord(lang = "eng", word = "He")
                writtenWord(lang = "eng", word = "Him")
                writtenWord(lang = "zho", script = "Piny", word = "ta1", weight = 1)
            }
            signified {
                type(Signified.Type.PRONOUN)
                writtenWord(lang = "eng", word = "She")
                writtenWord(lang = "eng", word = "Her")
                writtenWord(lang = "zho", script = "Piny", word = "ta1", weight = 1)
            }
        }
    }

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
        repository.add(generateEmptySignified(Signified.Type.VERB))
        val signified = generateEmptySignified(Signified.Type.NOUN)
        val id = repository.add(signified)
        repository.add(generateEmptySignified(Signified.Type.ADJECTIVE))

        assertEquals(signified, repository[id])
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

    @Test
    fun testGetEntries_SuccessMultipleWitHoles() {
        addSignifiedsWithHoles()

        val entries = repository.entries

        assertEquals(3, entries.size)
        assertEquals(0, entries.filter { (_, signified) -> signified.type == Signified.Type.ADJECTIVE }.size)
        assertEquals(1, entries.filter { (_, signified) -> signified.type == Signified.Type.NOUN }.size)
        assertEquals(2, entries.filter { (_, signified) -> signified.type == Signified.Type.VERB }.size)
    }

    @Test
    fun testGetEntries_CantChangeInternalValues() {
        val signified = generateEmptySignified(Signified.Type.NOUN)
        repository.add(signified)

        val changeSignified = generateEmptySignified(Signified.Type.VERB)

        val entries = repository.entries

        entries.forEach { pair ->
            pair.setValue(changeSignified)
        }

        assertFalse(repository.containsValue(changeSignified))
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
    fun testPut_OutOfBoundsSuccess() {
        val signified = generateEmptySignified()

        repository.put(getFirstId(), signified)

        assertEquals(signified, repository[getFirstId()])
    }

    @Test
    fun testPut_ReplaceSuccess() {
        addSignifiedsWithHoles()
        val id = repository.add(generateEmptySignified(Signified.Type.NOUN))
        val newSignified = generateEmptySignified(Signified.Type.EXCLAMATION)

        repository.put(id, newSignified)

        assertEquals(newSignified, repository[id])
    }

    @Test
    fun testPut_PreviouslyDisabled() {
        val removedId = addSignifiedsWithHoles()
        val newSignified = generateEmptySignified(Signified.Type.EXCLAMATION)

        repository.put(removedId, newSignified)

        assertEquals(newSignified, repository[removedId])
    }

    @Test
    fun testPutAll_Success() {
        val id1 = repository.add(generateEmptySignified(Signified.Type.NOUN))
        val id2 = repository.add(generateEmptySignified(Signified.Type.NOUN))
        val id3 = repository.add(generateEmptySignified(Signified.Type.NOUN))

        repository.putAll(hashMapOf(
            id1 to generateEmptySignified(Signified.Type.ADJECTIVE),
            id2 to generateEmptySignified(Signified.Type.ADJECTIVE),
            id3 to generateEmptySignified(Signified.Type.ADJECTIVE)
        ))

        assertEquals(3, repository.size)
        assertEquals(3, repository.values.filter { it.type == Signified.Type.ADJECTIVE }.size)
    }

    @Test
    fun testRemove_Success() {
        val firstId = repository.add(generateEmptySignified())
        val removedId = repository.add(generateEmptySignified())
        val lastId = repository.add(generateEmptySignified())

        repository.remove(removedId)

        assertNotNull(repository[firstId])
        assertNull(repository[removedId])
        assertNotNull(repository[lastId])
    }

    @Test
    fun testLocales_Success() {
        addSignifiedForFindTests()

        val locales = repository.locales

        assertEquals(4, locales.size)
        assertTrue(locales.contains(Signifier.Locale(lang = "eng")))
        assertTrue(locales.contains(Signifier.Locale(lang = "zho", script = "Hans")))
        assertTrue(locales.contains(Signifier.Locale(lang = "zho", script = "Hant")))
        assertTrue(locales.contains(Signifier.Locale(lang = "zho", script = "Piny")))
    }

    @Test
    fun testFind_ByTypeSuccess() {
        addSignifiedForFindTests()

        val result = repository.findByAll {
            // Nest a few times, just because
            all {
                any {
                    type(Signified.Type.PRONOUN)
                }
            }
        }

        assertFalse(result.isEmpty())
        result.forEach { (_, signified) ->
            assertEquals(Signified.Type.PRONOUN, signified.type)
        }
    }

    @Test
    fun testFind_ByWrittenWordSuccess() {
        addSignifiedForFindTests()

        val result = repository.findByAny {
            all {
                any {
                    hasWrittenWord(lang = "zho", script = "Hans")
                }
            }
        }

        assertFalse(result.isEmpty())
        result.forEach { (_, signified) ->
            var hasMatchingSignifier = false
            signified.signifiers.forEach signifierLoop@ { signifier ->
                if (signifier !is WrittenWord) {
                    return@signifierLoop
                }

                if (signifier.locale.lang.equals("zho", ignoreCase = true) && signifier.locale.script.equals("Hans", ignoreCase = true)) {
                    hasMatchingSignifier = true
                    return@signifierLoop
                }
            }
            assertTrue(hasMatchingSignifier, "Signified needs to have at least one WrittenWord(lang = \"zho\", script = \"Hans\"): ${signified}")
        }
    }
}
