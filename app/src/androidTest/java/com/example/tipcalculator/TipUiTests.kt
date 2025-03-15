package com.example.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test



class TipUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalcLayout()
            }
        }

        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("100")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        val expectedTip = 20.00
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found."
        )

    }

}