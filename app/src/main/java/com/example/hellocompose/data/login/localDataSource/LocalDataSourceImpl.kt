package com.example.hellocompose.data.login.localDataSource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.hellocompose.data.login.model.Token
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.util.ProtoTokenSerializer
import com.example.hellocompose.data.util.UserPreferencesSerializer
import com.example.hellocompose.data.util.convertToUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(private val context: Context) : LocalDataSource {
    override suspend fun saveUser(model: UserAccount) {
        context.userPreferencesStore.updateData {
            Log.d("Nurs", model.userId)
            with(model) {
                it.toBuilder()
                    .setBirthday(birthday)
                    .setId(userId)
                    .setFirstName(firstName)
                    .setSex(sex)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPhoneNumber(phoneNumber)
                    .clearCards()
                    .addAllCards(
                        cards.map {
                            ProtoCards.newBuilder().setCardId(it.cardId).setCardType(it.cardType)
                                .build()
                        }
                    )
                    .build()
            }
        }
    }

    override suspend fun saveToken(data: Token) {
        context.tokenStore.updateData {
            it.toBuilder().setAccessToken(data.accessToken).setRefreshToken(data.refreshToken)
                .build()
        }
    }

    override suspend fun getPhoneNumber(): String {
        val exampleCounterFlow: Flow<String> = context.userPreferencesStore.data
            .map { preferences ->
                preferences.convertToUser().phoneNumber
            }

       return exampleCounterFlow.first()
    }


    override suspend fun isUserSavedToLocalStorage(): Flow<UserAccount> {

        val exampleCounterFlow: Flow<UserAccount> = context.userPreferencesStore.data
            .map { preferences ->
                preferences.convertToUser()
            }
        return exampleCounterFlow
    }

}

private val Context.userPreferencesStore: DataStore<ProtoUser> by dataStore(
    fileName = "user_info.pb",
    serializer = UserPreferencesSerializer,
)
private val Context.tokenStore: DataStore<ProtoToken> by dataStore(
    fileName = "token.pb",
    serializer = ProtoTokenSerializer,
)