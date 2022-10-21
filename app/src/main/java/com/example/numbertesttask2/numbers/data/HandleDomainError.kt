package com.example.numbertesttask2.numbers.data

import com.example.numbertesttask2.numbers.domain.HandleError
import com.example.numbertesttask2.numbers.domain.NoInternetConnectionException
import com.example.numbertesttask2.numbers.domain.ServiceUnavailableException
import java.net.UnknownHostException

class HandleDomainError : HandleError<Exception> {
    override fun handle(e: Exception) = when (e) {
        is UnknownHostException -> NoInternetConnectionException()
        else -> ServiceUnavailableException()
    }
}