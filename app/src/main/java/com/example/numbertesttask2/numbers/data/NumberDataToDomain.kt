package com.example.numbertesttask2.numbers.data

import com.example.numbertesttask2.numbers.domain.NumberFact

class NumberDataToDomain:  NumberData.Mapper<NumberFact>{
    override fun map(id: String, fact: String) = NumberFact(id, fact)
}