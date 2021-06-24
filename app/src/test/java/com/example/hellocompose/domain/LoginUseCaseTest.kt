package com.example.hellocompose.domain

import com.example.hellocompose.StabData
import com.example.hellocompose.StabData.data
import com.example.hellocompose.data.login.LoginRepo
import com.example.hellocompose.ui.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class LoginUseCaseTest : TestCase() {
    @Test
    fun `test login function if result success`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.signIn(data)
            } returns flowOf(Result.Loading, Result.Success("true"))

            /**when*/
            val list = LoginUseCase(loginRepo).login(
                "", "", "", "", ""
            ).toList()

            /***expected*/
            val expectedList =
                listOf(
                    LoginScreen.Loading, LoginScreen.NavigateToVerifyPhoneNumber("")
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `test login function if result error`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.signIn(data)
            } returns flowOf(Result.Loading, Result.Error(StabData.error))

            /**when*/
            val list = LoginUseCase(loginRepo).login(
                "", "", "", "", ""
            ).toList()

            /***expected*/
            val expectedList =
                listOf(
                    LoginScreen.Loading, LoginScreen.Error(StabData.error)
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `test getToken function if result error`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.getToken("123")
            } returns flowOf(Result.Loading, Result.Error(StabData.error))

            /**when*/
            val list = LoginUseCase(loginRepo).getToken("123").toList()

            /***expected*/
            val expectedList =
                listOf(
                    VerifyPhoneNumberScreen.ShowLoading,
                    VerifyPhoneNumberScreen.ShowError(StabData.error)
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `test getToken function if result success`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.getToken("123")
            } returns flowOf(Result.Loading, Result.Success(Unit))

            /**when*/
            val list = LoginUseCase(loginRepo).getToken("123").toList()

            /***expected*/
            val expectedList =
                listOf(
                    VerifyPhoneNumberScreen.ShowLoading,
                    VerifyPhoneNumberScreen.NavigateToMainScreen(Unit)
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `test resendSms function if result error`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.resendSMS()
            } returns Result.Error(StabData.error)

            /**when*/
            val list = LoginUseCase(loginRepo).resendSms().toList()

            /***expected*/
            val expectedList =
                listOf(
                    VerifyPhoneNumberScreen.ShowError(StabData.error)
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `test resendSms function if result success`() {
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.resendSMS()
            } returns Result.Success(Unit)

            /**when*/
            val list = LoginUseCase(loginRepo).resendSms().toList()

            /***expected*/
            val expectedList =
                listOf(
                    VerifyPhoneNumberScreen.MessageSentToast(Unit)
                )
            Assert.assertEquals(expectedList, list)
        }
    }


}