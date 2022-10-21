package com.example.numbertesttask2.numbers.data

import java.net.UnknownHostException

interface NumbersCloudDataSource : FetchNumber {

    suspend fun randomNumber(): NumberData
}