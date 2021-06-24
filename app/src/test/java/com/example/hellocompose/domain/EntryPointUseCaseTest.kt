package com.example.hellocompose.domain

import com.example.hellocompose.StabData
import com.example.hellocompose.data.login.LoginRepo
import com.example.hellocompose.data.login.LoginRepoImpl
import com.example.hellocompose.ui.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class EntryPointUseCaseTest {
    @Test
    fun `testEntryPoint if user  not logged in`(){
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.isUserLoggedIn()
            } returns flowOf(Result.Loading,Result.Success(true))

            /**when*/
            val list = EntryPointUseCase(loginRepo).invoke().toList()

            /***expected*/
            val expectedList =
                listOf(
                    NavigationState.ShowLoading,NavigationState.NavigateToMainScreen
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `testEntryPoint if user is not logged in`(){
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.isUserLoggedIn()
            } returns flowOf(Result.Loading,Result.Success(false))

            /**when*/
            val list = EntryPointUseCase(loginRepo).invoke().toList()

            /***expected*/
            val expectedList =
                listOf(
                    NavigationState.ShowLoading,NavigationState.NavigateToLoginScreen
                )
            Assert.assertEquals(expectedList, list)
        }
    }

    @Test
    fun `testEntryPoint if error happened`(){
        runBlocking {
            val loginRepo = mockk<LoginRepo>()
            coEvery {
                loginRepo.isUserLoggedIn()
            } returns flowOf(Result.Loading,Result.Error(StabData.error))

            /**when*/
            val list = EntryPointUseCase(loginRepo).invoke().toList()

            /***expected*/
            val expectedList =
                listOf(
                    NavigationState.ShowLoading,NavigationState.ShowError(StabData.error)
                )
            Assert.assertEquals(expectedList, list)
        }
    }
}