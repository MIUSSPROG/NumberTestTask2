package com.example.numbertesttask2.numbers.data

import com.example.numbertesttask2.numbers.data.cache.NumbersCacheDataSource
import com.example.numbertesttask2.numbers.domain.HandleError
import com.example.numbertesttask2.numbers.domain.NumberFact

interface HandleDataRequest{

    suspend fun handle(block: suspend () -> NumberData): NumberFact

    class Base(
        private val cacheDataSource: NumbersCacheDataSource,
        private val mapperToDomain: NumberData.Mapper<NumberFact>,
        private val handleError: HandleError<Exception>
    ): HandleDataRequest {
        override suspend fun handle(block: suspend () -> NumberData): NumberFact {
            return try {
                val result = block.invoke()
                cacheDataSource.saveNumberFact(result)
                result.map(mapperToDomain)
            }catch (e: Exception) {
                throw handleError.handle(e)
            }
        }

    }
}