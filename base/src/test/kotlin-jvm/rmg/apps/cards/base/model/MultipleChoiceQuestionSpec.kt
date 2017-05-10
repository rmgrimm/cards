package rmg.apps.cards.base.model

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertTrue

object MultipleChoiceQuestionSpec: Spek({
    describe("a multiple choice question") {
        val locale = Signifier.Locale(lang = "eng")
        val answerOptions = (1..5).map { WrittenWord(locale, it.toString()) }
        val questionSignified = Signified(Signified.Type.NOUN, listOf(answerOptions[0]))
        val handler = mock<Question.() -> Unit>()

        val question = MultipleChoiceQuestion(questionSignified, answerOptions, handler)

        on("assigning a selected index") {
            question.selectedIndex = 0

            it("should call the handler") {
                then(handler).should().invoke(eq(question))
            }

            it("should show answered") {
                assertTrue {
                    question.isAnswered
                }
            }
        }
    }
})
