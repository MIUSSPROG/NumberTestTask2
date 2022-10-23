package com.example.numbertesttask2.numbers.data

import com.example.numbertesttask2.numbers.data.cache.NumbersCacheDataSource
import com.example.numbertesttask2.numbers.data.cloud.NumbersCloudDataSource
import com.example.numbertesttask2.numbers.domain.NumberFact
import com.example.numbertesttask2.numbers.domain.NumbersRepository

class BaseNumbersRepository(
    private val cloudDataSource: NumbersCloudDataSource,
    private val cacheDataSource: NumbersCacheDataSource,
    private val mapperToDomain: NumberData.Mapper<NumberFact>,
    private val handleDataRequest: HandleDataRequest
) : NumbersRepository {

    override suspend fun allNumbers(): List<NumberFact> {
        val data = cacheDataSource.allNumbers()
        return data.map { it.map(mapperToDomain) }
    }

    override suspend fun numberFact(number: String): NumberFact {
       return  handleDataRequest.handle {
            val dataSource = if (cacheDataSource.contains(number))
                cacheDataSource
            else
                cloudDataSource
            dataSource.number(number)
        }
    }

    override suspend fun randomNumberFact(): NumberFact {
        return handleDataRequest.handle {
            cloudDataSource.randomNumber()
        }
    }

}

