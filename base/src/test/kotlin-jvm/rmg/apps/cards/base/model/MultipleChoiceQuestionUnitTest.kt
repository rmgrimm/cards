package rmg.apps.cards.base.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class MultipleChoiceQuestionUnitTest {

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    lateinit var question: MultipleChoiceQuestion

    @Before
    fun setUpQuestion() {
        val locale = Signifier.Locale(lang = "eng")
        val answerOptions = (1..5).map { WrittenWord(locale, it.toString()) }
        val questionSignified = Signified(Signified.Type.NOUN, listOf(answerOptions[0]))

        question = MultipleChoiceQuestion(questionSignified, answerOptions) { idx, correct ->
            println("Testing answer selected; idx: $idx, correct: $correct")
        }
    }

    @Test
    fun testInit_NoCorrectAnswer() {
        expectedException.expect(IllegalArgumentException::class.java)

        question = MultipleChoiceQuestion(
            Signified(Signified.Type.NOUN, listOf(WrittenWord(Signifier.Locale("eng"), "test"))),
            emptyList())
    }

    @Test
    fun testIsAnswered_Unanswered() {
        assertFalse(question.isAnswered)
    }

    @Test
    fun testIsAnswered_Answered() {
        question.selectedIndex = 0

        assertTrue(question.isAnswered)
    }

    @Test
    fun testSelectedIndex_CantChangeAnswer() {
        expectedException.expect(IllegalStateException::class.java)
        expectedException.expectMessage("Selected answer already set to 0, cannot replace with 1!")

        question.selectedIndex = 0
        question.selectedIndex = 1
    }

    @Test
    fun testSelectedIndex_IndexOutOfRangeLow() {
        expectedException.expect(IndexOutOfBoundsException::class.java)

        question.selectedIndex = -1
    }

    @Test
    fun testSelectedIndex_IndexOutOfRangeHigh() {
        expectedException.expect(IndexOutOfBoundsException::class.java)

        question.selectedIndex = 5
    }

    @Test
    fun testSelectedIndex_CannotSetNull() {
        expectedException.expect(IllegalArgumentException::class.java)

        question.selectedIndex = null
    }

    @Test
    fun testIsCorrect_NoAnswer() {
        assertNull(question.isCorrect)
    }

    @Test
    fun testIsCorrect_Incorrect() {
        question.selectedIndex = 1

        assertFalse(question.isCorrect!!)
    }

    @Test
    fun testIsCorrect_Correct() {
        question.selectedIndex = 0

        assertTrue(question.isCorrect!!)
    }

}
