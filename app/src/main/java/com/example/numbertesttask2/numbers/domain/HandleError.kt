package com.example.numbertesttask2.numbers.domain

import com.example.numbertesttask2.R
import com.example.numbertesttask2.numbers.presentation.ManageResources

interface HandleError<T> {

    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources): HandleError<String>{
        override fun handle(e: Exception) =
            manageResources.string(when(e){
                is NoInternetConnectionException -> R.string.no_connection_message
                else -> R.string.service_is_unavailable
            })
    }
}