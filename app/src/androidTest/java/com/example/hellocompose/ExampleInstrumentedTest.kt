package com.example.hellocompose

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.util.countTimerText
import com.example.hellocompose.ui.util.disabledSendNewSmsButton
import com.example.hellocompose.ui.util.requestNewSmsButton
import com.example.hellocompose.ui.util.requestNewSmsText
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity3>()

    @Test
    @ExperimentalAnimatedInsets
    fun authScreen_count_down_test() {
        val sendNewSmsCodeButtonEnabled = mutableStateOf(false)
        composeTestRule.setContent {
            authScreen(
                loading = false,
                showError = Pair(false, ""),
                sendNewSmsCodeButtonEnabled = sendNewSmsCodeButtonEnabled.value,
                disableSendNewCodeView = {
                    sendNewSmsCodeButtonEnabled.value = true
                },
                enableSendNewCodeView = {
                    sendNewSmsCodeButtonEnabled.value = false
                },
                sendNewSmsCode = { },
                getToken = { },
                detachErrorSnackbar = {

                },
                getLastSavedTime = {
                    50
                }
            )
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.onNodeWithContentDescription(requestNewSmsButton).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(requestNewSmsText)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(disabledSendNewSmsButton)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithContentDescription(requestNewSmsButton)
            .performClick()
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule
            .onNode(hasAnyChild(hasContentDescription(disabledSendNewSmsButton)))
            .assertIsDisplayed()
        composeTestRule
            .onNode(hasAnyChild(hasContentDescription(countTimerText)))
            .assertIsDisplayed()
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.mainClock.advanceTimeBy(1000)
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription(requestNewSmsButton).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(requestNewSmsText)
            .assertIsDisplayed()

    }
}