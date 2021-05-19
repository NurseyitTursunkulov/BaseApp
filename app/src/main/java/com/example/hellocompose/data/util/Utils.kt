package com.example.hellocompose.data.util


class UserIsAlreadyRegistered():Exception()
class ResponseIsNotSuccessfulException(message:String):Exception(message)
class ExceptionInResponseBody(message:String = ""):Exception(message)

