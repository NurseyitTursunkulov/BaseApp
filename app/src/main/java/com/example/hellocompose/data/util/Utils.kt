package com.example.hellocompose.data.util

import com.example.hellocompose.ui.util.Result
import kotlinx.coroutines.flow.FlowCollector


class UserIsAlreadyRegistered():Exception()
class ResponseIsNotSuccessfulException(message:String):Exception(message)
class ExceptionInResponseBody(message:String = ""):Exception(message)

suspend inline fun <T, D> FlowCollector<Result<T>>.onSuccessResponse(
    r: Result<D>,
    onSuccess: (D) -> Unit
) {
    when (r) {
        is Result.Success -> {
            onSuccess(r.data)
        }
        is Result.Error -> {
            emit(Result.Error(r.exception))
        }
        Result.Loading -> {
            emit(Result.Loading)
        }
    }
}
typealias Second = Int