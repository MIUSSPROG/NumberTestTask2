package com.example.numbertesttask2.numbers.data.cache

import com.example.numbertesttask2.numbers.data.NumberData

interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumberFact(numberData: NumberData) // todo saveNumber rename
}

interface FetchNumber{
    suspend fun number(number: String): NumberData
}