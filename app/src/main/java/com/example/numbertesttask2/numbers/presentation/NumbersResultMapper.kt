package com.example.numbertesttask2.numbers.presentation

import com.example.numbertesttask2.numbers.domain.NumberFact
import com.example.numbertesttask2.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumberCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) {
        communications.showState(
            if (errorMessage.isEmpty()) {
                if (list.isNotEmpty()) {
                    val numberList = list.map { it.map(mapper) }
                    communications.showList(numberList)
                }
                UiState.Success()
            } else
                UiState.ShowError(errorMessage)
        )
    }
}

