package com.example.numbertesttask2.numbers.data.cache
import com.example.numbertesttask2.numbers.data.NumberData

class NumberDataToCache : NumberData.Mapper<NumberCache> {
    override fun map(id: String, fact: String): NumberCache = NumberCache(id, fact, System.currentTimeMillis())
}