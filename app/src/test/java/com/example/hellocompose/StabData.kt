package com.example.hellocompose

import com.example.hellocompose.data.login.model.Card
import com.example.hellocompose.data.login.model.CardPrint
import com.example.hellocompose.data.login.model.UserAccount

object StabData {
     val error = Exception()
     val data = UserAccount(
        phoneNumber = "",
        firstName = "",
        lastName = "",
        middleName = "",
        email = "",
        birthday = "",
        regChannel = 1,
        sex = 1,
        cards = listOf(),
    )
    val card = Card(
        id = 1,
        fromPrint =2 ,
        toPrint = 10,
        cardsPrinted = 9,
        cardsFree = 7,
        timePrintedUtc = "",
        printedUserFullName = "",
        userId = "sds",
    )
    val cardPrint = CardPrint(
        2,11
    )
}