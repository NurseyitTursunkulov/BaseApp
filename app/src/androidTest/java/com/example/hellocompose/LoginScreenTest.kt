package com.example.hellocompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.hellocompose.ui.authScreen.circularProgressIndicator
import com.example.hellocompose.ui.changeStatusBarColor
import com.example.hellocompose.ui.loginScreen.*
import com.example.hellocompose.ui.theme.HelloComposeTheme
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
@ExperimentalAnimatedInsets
class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity3>()

    @Test
    fun loginScreenValidateEmptyInputTest() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->

                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nameErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.mainClock.advanceTimeBy(10000)
        Thread.sleep(4000)
    }

    @Test
    fun loginScreenValidateInputTestWithIncorrectPhone() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("nurs")
        composeTestRule.onNodeWithTag(surNameTag).performTextInput("turs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("0777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs@mail.ru")

        composeTestRule
            .onNodeWithTag(birthDateButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertDoesNotExist()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
        Thread.sleep(4000)
    }

    @Test
    fun loginScreenValidateInputTestWithIncorrectMail() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("nurs")
        composeTestRule.onNodeWithTag(surNameTag).performTextInput("turs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("996777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs.u")

        composeTestRule
            .onNodeWithTag(birthDateButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertDoesNotExist()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
    }

    @Test
    fun loginScreenValidateInputTestWithEmptyName() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

//        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("")
        composeTestRule.onNodeWithTag(surNameTag).performTextInput("turs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("996777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs@mail.ru")

        composeTestRule
            .onNodeWithTag(birthDateButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertDoesNotExist()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nameErrorTag).assertIsDisplayed()
    }

    @Test
    fun loginScreenValidateInputTestWithEmptySurname() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("nurs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("996777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs@mail.ru")

        composeTestRule
            .onNodeWithTag(birthDateButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertDoesNotExist()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsDisplayed()
    }

    @Test
    fun loginScreenValidateInputTestWithEmptyBirthDate() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("nurs")
        composeTestRule.onNodeWithTag(surNameTag).performTextInput("turs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("996777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs@mail.ru")

//        composeTestRule
//            .onNodeWithTag(birthDateButtonTag)
//            .performClick()
//        composeTestRule.mainClock.advanceTimeBy(5000)
//        composeTestRule.mainClock.autoAdvance = true
//        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertDoesNotExist()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithTag(nameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(nametextFieldTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(emailErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(emailTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(registerButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(birthDateButtonTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(surNameErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberErrorTag).assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(phoneNumberTag).assertIsDisplayed()

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(birthDateErrorTag).assertIsDisplayed()
    }

    @Test
    fun loginScreenValidateInputTest() {
        composeTestRule.setContent {
            val showError = remember { mutableStateOf(Pair(false, "")) }
            val loading = remember { mutableStateOf(false) }
            changeStatusBarColor()
            HelloComposeTheme {
                loginScreen(
                    loading.value,
                    showError.value,
                    setDecorFitsSystemWindows = {},
                    closeErrorView = {
                    },
                    onLoginClicl = { name,
                                     surName,
                                     phoneNumber,
                                     email,
                                     birthDate ->
                        loading.value = true
                    }
                )
            }
        }

        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.mainClock.advanceTimeBy(5000)

        composeTestRule.onNodeWithTag(nametextFieldTag).performTextInput("nurs")
        composeTestRule.onNodeWithTag(surNameTag).performTextInput("turs")
        composeTestRule.onNodeWithTag(phoneNumberTag).performTextInput("996777491567")
        composeTestRule.onNodeWithTag(emailTag).performTextInput("nurs@mail.ru")

        composeTestRule
            .onNodeWithTag(birthDateButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.mainClock.autoAdvance = true
        composeTestRule.onNodeWithText("OK").performClick()
        composeTestRule
            .onNodeWithTag(registerButtonTag)
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(5000)
        composeTestRule.onNodeWithContentDescription(circularProgressIndicator).assertIsDisplayed()
        composeTestRule.mainClock.advanceTimeBy(5000)
        Thread.sleep(4000)
    }
}