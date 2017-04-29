package rmg.apps.cards.base.model

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness
import rmg.apps.cards.base.SignifiedRepository
import rmg.apps.cards.base.SignifierCriteria
import kotlin.test.*

class MultipleChoiceQuestionGeneratorUnitTest {

    @Rule
    @JvmField
    var expectedException = ExpectedException.none()

    @Rule
    @JvmField
    var mockito = MockitoJUnit.rule()
        .strictness(Strictness.STRICT_STUBS)

    lateinit var generator: MultipleChoiceQuestion.Generator<Int, Unit>

    @Mock
    lateinit var repository: SignifiedRepository<Int, Unit>

    @Mock
    lateinit var answerCriteria: SignifierCriteria

    @Before
    fun setUp() {
        generator = MultipleChoiceQuestion.Generator(
            repository = repository,
            numAnswers = 5,
            answerCriteria = answerCriteria)
    }

    @Test
    fun testGenerateQuestion_SignifiedMustHaveSignifiers() {
        expectedException.expect(IllegalArgumentException::class.java)

        val questionSignified = Signified(type = Signified.Type.NOUN,
            signifiers = emptyList())

        generator.generateQuestion(questionSignified)
    }

    @Test
    fun testGenerateQuestion_Success() {
        val questionSignifier = WrittenWord(lang = "eng", word = "Test")
        val questionSignified = Signified(type = Signified.Type.NOUN,
            signifiers = listOf(questionSignifier))

        given(answerCriteria.match(eq(questionSignifier))).willReturn(true)

        val question = generator.generateQuestion(questionSignified)

        assertTrue(question is MultipleChoiceQuestion)
    }


}
