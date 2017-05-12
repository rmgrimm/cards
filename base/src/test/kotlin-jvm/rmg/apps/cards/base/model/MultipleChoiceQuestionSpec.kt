package rmg.apps.cards.base.model

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.then
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

object MultipleChoiceQuestionSpec: SubjectSpek<MultipleChoiceQuestion>({
    describe("a multiple choice question") {
        val locale = Locale(lang = "eng")
        val answerOptions = (1..5).map { WrittenWord(locale, it.toString()) }
        val correctAnswerIndex = 0
        val incorrectAnswerIndex = 1
        val questionSignified = Signified(Signified.Type.NOUN, listOf(answerOptions[correctAnswerIndex]))
        val handler = mock<Question.() -> Unit>()

        beforeEachTest { reset(handler) }

        subject {
            MultipleChoiceQuestion(
                questionSignified = questionSignified,
                answerSignifiers = answerOptions,
                handler = handler)
        }

        it("should not show answered initially") {
            assertFalse { subject.isAnswered }
        }

        it("should not have any correct/incorrect value") {
            assertNull(subject.isCorrect)
        }

        on("selecting a correct answer") {
            subject.selectedIndex = correctAnswerIndex

            it("should call the handler") {
                then(handler).should().invoke(eq(subject))
            }

            it("should show answered") {
                assertTrue { subject.isAnswered }
            }

            it("should indicate correct") {
                assertTrue { subject.isCorrect!! }
            }
        }

        on("selecting an incorrect answer") {
            subject.selectedIndex = incorrectAnswerIndex

            it("should call the handler") {
                then(handler).should().invoke(eq(subject))
            }

            it("should show answered") {
                assertTrue { subject.isAnswered }
            }

            it("should indicate that the answer is incorrect") {
                assertFalse { subject.isCorrect!! }
            }
        }
    }
})
