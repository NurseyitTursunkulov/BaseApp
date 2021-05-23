package com.example.hellocompose.data.login.localDataSource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codelab.android.datastore.UserPreferences
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.login.model.UserAccount
import com.example.hellocompose.data.util.UserPreferencesSerializer
import com.example.hellocompose.data.util.convertToUser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class LocalDataSourceImpl(private val context: Context) : LocalDataSource {

    override suspend fun saveUser(model: UserAccount)  {
            context.userPreferencesStore.updateData {
                Log.d("Nurs",model.userId)
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
                                ProtoCards.newBuilder().setCardId(it.cardId).setCardType(it.cardType).build()
                            }
                        )
                        .build()
                }
            }
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