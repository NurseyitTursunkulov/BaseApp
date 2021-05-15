package com.example.hellocompose.data.login.model

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("id") val id: Int,
    @SerializedName("fromPrint") val fromPrint: Int,
    @SerializedName("toPrint") val toPrint: Int,
    @SerializedName("cardsPrinted") val cardsPrinted: Int,
    @SerializedName("cardsFree") val cardsFree: Int,
    @SerializedName("timePrintedUtc") val timePrintedUtc: String,
    @SerializedName("printedUserFullName") val printedUserFullName: String,
    @SerializedName("userId") val userId: String
)