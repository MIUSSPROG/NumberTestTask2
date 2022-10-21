package com.example.numbertesttask2.numbers.data

interface NumbersCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumberFact(numberData: NumberData) // todo saveNumber
}

interface FetchNumber{
    suspend fun number(number: String): NumberData
}