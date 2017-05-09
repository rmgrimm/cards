package rmg.apps.cards.backend

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness
import rmg.apps.cards.base.Quiz
import rmg.apps.cards.base.model.Question
import kotlin.test.*

class InMemoryQuizUnitTest {

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()

    @Rule
    @JvmField
    val mockito = MockitoJUnit.rule()
        .strictness(Strictness.STRICT_STUBS)

    @Mock
    lateinit var question1: Question

    @Mock
    lateinit var question2: Question

    lateinit var quiz: InMemoryQuiz

    @Before
    fun setUpQuiz() {
        quiz = InMemoryQuiz(listOf(question1, question2))
    }

    @Test
    fun testInit_Failure() {
        expectedException.expect(IllegalArgumentException::class.java)

        InMemoryQuiz(emptyList())
    }

    @Test
    fun testUserId_AlwaysUnit() {
        assertEquals(Unit, quiz.userId)
    }

    @Test
    fun testState_Unstarted() {
        assertEquals(Quiz.State.UNSTARTED, quiz.state)
    }

    @Test
    fun testState_InProgress() {
        quiz.nextQuestion()

        assertEquals(Quiz.State.STARTED, quiz.state)
    }

    @Test
    fun testState_Completed() {
        while (quiz.hasNextQuestion()) {
            quiz.nextQuestion()
        }

        assertEquals(Quiz.State.COMPLETED, quiz.state)
    }

}
