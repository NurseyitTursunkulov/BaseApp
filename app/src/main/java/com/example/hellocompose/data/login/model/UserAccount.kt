package com.example.hellocompose.data.login.model

import com.google.gson.annotations.SerializedName

data class UserAccount(
    @SerializedName("phoneNumber") var   phoneNumber                : String ,
    @SerializedName("firstName") var     firstName              : String,
    @SerializedName("lastName") var      lastName               : String= "",
    @SerializedName("middleName") var    middleName             : String = "",
    @SerializedName("email") var         email              : String,
    @SerializedName("birthday") var      birthday               : String,
    @SerializedName("regChannel") var    regChannel             : Int= 1,
    @SerializedName("sex") var           sex                : Int =1,
    @SerializedName("cards") var         cards              : List<Cards> = emptyList(),
    var userId :String = ""
)

data class Cards(
    @SerializedName("cardId") var cardId: Int,
    @SerializedName("cardType") var cardType: Int

)