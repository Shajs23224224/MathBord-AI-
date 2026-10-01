package com.mathbord.ai

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MainActivityNavigationTest {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun boardDestinationIsReachable() {
        rule.onNodeWithText("Pizarra").assertIsDisplayed().performClick()
        rule.onNodeWithText("Escribe una operación matemática aquí")
            .assertIsDisplayed()
        rule.onNodeWithText("Lápiz")
            .assertIsDisplayed()
        rule.onNodeWithText("Resaltador")
            .assertIsDisplayed()
        rule.onNodeWithText("Borrador")
            .assertIsDisplayed()
    }
}
