package com.example.hellocompose

import android.util.Log
import com.example.hellocompose.StabData.cardPrint
import com.example.hellocompose.StabData.data
import com.example.hellocompose.data.login.LoginRepoImpl
import com.example.hellocompose.data.login.localDataSource.LocalDataSource
import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.remoteDS.RemoteDS
import com.example.hellocompose.data.util.ExceptionInResponseBody
import com.example.hellocompose.ui.util.Result
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any

/**
1 Получить ID свободной карты
GET /api​/BaseCrmApi​/getCardFree
Если массив пришёл пустым необходима сгенерировать карты!!!
GET/ api​/BaseCrmApi​/getLastCardNumber (узнаём номер последней сгенерированной карты)
POST /api​/BaseCrmApi​/cardPrint
"fromPrint": номер последней сгенерированной карты + 1,
"toPrint": "fromPrint" + количество генерируемых карт

2 Нужно зарегистрировать его!!!
POST /api​/BaseCrmApi​/AccountRegister
(cardId: Свободная карта)
("cardType": 1)

3 Получить пароль по смс
POST /api​/BaseCrmApi​/sendSmsWithCode

4 Получить токен на 5 минут
PUT /api​/BaseCrmApi​/getToken
"clientName": "Номер телефона",
"password": "код отправленный в СМС " */
class LoginRepoImlTest {

    @Before
    fun stubLogs() {
        MockKAnnotations.init(this)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `if getCardFree returns error there must be zero emits`() {
        /**given*/
        runBlocking {
            val remoteDs = mockk<RemoteDS>()
            val localDS = mockk<LocalDataSource>() {
            }
            coEvery {
                localDS.isUserSavedToLocalStorage()
            } returns flowOf(data)
            coEvery { remoteDs.getCardFree() } returns Result.Error(StabData.error)
            coEvery { localDS.saveUser(data) } returns Unit

            /**when*/
            val list = LoginRepoImpl(remoteDs, localDS).signIn(data).toList()

            /***expected*/
            val expectedList =
                listOf(
                    Result.Loading,
                    Result.Error(StabData.error)
                )
            Assert.assertEquals(expectedList, list)

            coVerify {
                remoteDs.getCardFree()
            }
            coVerify(exactly = 0) {
                localDS.saveUser(data)
                remoteDs.registerAccount(any())
                remoteDs.getLastCardNumber()
                remoteDs.generateCards(any())
            }
        }
    }

    @Test
            /** case remoteDs.generateCards() returns Result.Error  */
    fun `if getCardFree returns emptyList should make request to generate new cards`() {
        runBlocking {
            val remoteDs = mockk<RemoteDS>()
            val localDS = mockk<LocalDataSource>() {
            }
            coEvery {
                localDS.isUserSavedToLocalStorage()
            } returns flowOf(data)
            coEvery { localDS.saveUser(data) } returns Unit
            val data1 = listOf<Card>()
            coEvery { remoteDs.getCardFree() } returns Result.Success(data1)
            coEvery { remoteDs.getLastCardNumber() } returns Result.Success(1)
            coEvery { remoteDs.generateCards(any()) } returns Result.Error(
                StabData.error
            )

            val list = LoginRepoImpl(remoteDs, localDS).signIn(data).toList()

            val expectedList =
                listOf(
                    Result.Loading,
                    Result.Error(ExceptionInResponseBody("returned empty cards"))
                )
            list.forEachIndexed { index, result ->
                MatcherAssert.assertThat(
                    result,
                    CoreMatchers.instanceOf<Any>(expectedList[index]::class.java)
                )
            }

            coVerify {
                remoteDs.getCardFree()
                remoteDs.getLastCardNumber()
                remoteDs.generateCards(
                    CardPrint(
                        1 + 1,
                        1 + 10
                    )
                )
            }

            coVerify(exactly = 0) {
                localDS.saveUser(data)
                remoteDs.registerAccount(any())
            }
        }
    }

    @Test
            /** case remoteDs.generateCards() returns Result.Success  */
    fun `if getCardFree returns emptyList should make request to generate new cards2`() {
        runBlocking {
            val remoteDs = mockk<RemoteDS>()
            val data1 = listOf<Card>()
            val localDS = mockk<LocalDataSource>()
            coEvery {
                localDS.isUserSavedToLocalStorage()
            } returns flowOf(data)
            coEvery { localDS.saveUser(data) } returns Unit
            coEvery { remoteDs.getCardFree() } returns Result.Success(data1)
            coEvery { remoteDs.sendSmsCode("") } returns Result.Success(Unit)
            coEvery { remoteDs.getLastCardNumber() } returns Result.Success(1)
            coEvery { remoteDs.registerAccount(data) } returns Result.Success("")
            coEvery { remoteDs.generateCards(cardPrint) } returns Result.Success(
                listOf(StabData.card.copy(id = 10, fromPrint = 11))
            )

            val list = LoginRepoImpl(remoteDs, localDS).signIn(data).toList()

            val expectedList =
                listOf(
                    Result.Loading,
                    Result.Success("")
                )
            list.forEachIndexed { index, result ->
                MatcherAssert.assertThat(
                    result,
                    CoreMatchers.instanceOf<Any>(expectedList[index]::class.java)
                )
            }
            Assert.assertEquals(expectedList, list)
            coVerifySequence {
                remoteDs.getCardFree()
                remoteDs.getLastCardNumber()
                remoteDs.generateCards(cardPrint)
                remoteDs.registerAccount(data)
                localDS.saveUser(data)
                remoteDs.sendSmsCode("")
            }
        }
    }

    @Test
    fun `if getCardFree returns data other functions should not be called`() {
        /**given*/
        runBlocking {
            val remoteDs = mockk<RemoteDS>()
            val localDS = mockk<LocalDataSource>()
            coEvery {
                localDS.isUserSavedToLocalStorage()
            } returns flowOf(data)
            coEvery { localDS.saveUser(data) } returns Unit
            coEvery { remoteDs.sendSmsCode("") } returns Result.Success(Unit)
            coEvery { remoteDs.getCardFree() } returns Result.Success(listOf(StabData.card))
            coEvery { remoteDs.registerAccount(data) } returns Result.Success("jjj")

            /**when*/
            val list = LoginRepoImpl(remoteDs, localDS).signIn(data).toList()

            /**expected*/
            val expectedList =
                listOf(
                    Result.Loading,
                    Result.Success("jjj")
                )
            Assert.assertEquals(expectedList, list)

            coVerify {
                localDS.saveUser(data)
                remoteDs.getCardFree()
                remoteDs.registerAccount(any())
                localDS.saveUser(data)
                //todo test to send sms code
            }
            coVerify(exactly = 0) {
                remoteDs.getLastCardNumber()
                remoteDs.generateCards(any())
            }
        }
    }

}
