package com.example.numbertesttask2.numbers.domain

import com.example.numbertesttask2.numbers.presentation.NumberUi

class NumberUiMapper: NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi = NumberUi(id, fact)
}