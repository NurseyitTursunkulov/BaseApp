package com.example.hellocompose.data.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codelab.android.datastore.UserPreferences
import com.example.hellocompose.data.login.localDataSource.ProtoCards
import com.example.hellocompose.data.login.localDataSource.ProtoUser
import com.example.hellocompose.data.login.model.Cards
import com.example.hellocompose.data.login.model.UserAccount
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<ProtoUser> {
    override val defaultValue: ProtoUser = ProtoUser.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): ProtoUser {
        try {
            return ProtoUser.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ProtoUser, output: OutputStream) = t.writeTo(output)
}
fun ProtoUser.convertToUser():UserAccount{
    return UserAccount(
        phoneNumber = this.phoneNumber,
                firstName = this.firstName  ,
                lastName = this.lastName   ,
                middleName = this.middleName ,
                email = this.email      ,
                birthday = this.birthday   ,
                regChannel = this.regChannel ,
                sex = this.sex        ,
                cards = this.cardsList.map {
                    it.convertToCard()
                }     ,
        userId = this.id
    )
}
fun ProtoCards.convertToCard():Cards{
    return Cards(
        cardId = this.cardId,
            cardType = this.cardType
    )
}

object ProtoUserSerializer : Serializer<ProtoUser> {
    override val defaultValue: ProtoUser = ProtoUser.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): ProtoUser {
        try {
            return ProtoUser.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: ProtoUser, output: OutputStream) = t.writeTo(output)
}