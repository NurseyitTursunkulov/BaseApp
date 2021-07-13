package com.example.hellocompose

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.core.view.WindowCompat
import com.example.hellocompose.ui.authScreen.authScreen
import com.example.hellocompose.ui.authScreen.circularProgressIndicator
import com.example.hellocompose.ui.changeStatusBarColor
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.example.hellocompose.ui.util.*
import com.example.hellocompose.ui.util.requestNewSmsButton
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import kotlinx.coroutines.delay
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalAnimatedInsets
class AuthScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity3>()

    @Test
    fun authScreenGetTokenTestWithCorrectCode() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val sendNewSmsCodeButtonEnabled = remember { mutableStateOf(false) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                authScreen(
                    loading = loading.value,
                    showError = showError.value,
                    sendNewSmsCodeButtonEnabled = sendNewSmsCodeButtonEnabled.value,
                    disableSendNewCodeView = {
                        sendNewSmsCodeButtonEnabled.value = true
                    },
                    enableSendNewCodeView = {
                        sendNewSmsCodeButtonEnabled.value = false
                    },
                    sendNewSmsCode = {
                        loading.value = true
                    },
                    getToken = {
                        loading.value = true
                    },
                    detachErrorSnackbar = {

                    },
                    getLastSavedTime = {
                        50
                    }
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(requestNewSmsButton).assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(requestNewSmsText)
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription(disabledSendNewSmsButton)
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag(SMSCodeView).performTextInput("4444")
        composeTestRule
            .onNodeWithContentDescription(getTokenButton)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertIsDisplayed()
        composeTestRule.mainClock.advanceTimeBy(5000)
    }

    @Test
    fun authScreen_count_down_test() {
        val sendNewSmsCodeButtonEnabled = mutableStateOf(false)
        composeTestRule.setContent {
            changeStatusBarColor()
            HelloComposeTheme {
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
                    sendNewSmsCode = {

                    },
                    getToken = { },
                    detachErrorSnackbar = {

                    },
                    getLastSavedTime = {
                        50
                    }
                )
            }
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