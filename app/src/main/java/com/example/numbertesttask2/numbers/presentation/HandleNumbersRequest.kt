package com.example.numbertesttask2.numbers.presentation

import android.view.View
import androidx.lifecycle.viewModelScope
import com.example.numbertesttask2.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface HandleNumbersRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> NumbersResult
    )

    class Base(
        private val dispatchers: DispatchersList,
        private val communications: NumberCommunications,
        private val numbersResultMapper: NumbersResult.Mapper<Unit>
    ) : HandleNumbersRequest {
        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend () -> NumbersResult
        ) {
            communications.showProgress(View.VISIBLE)
            coroutineScope.launch(dispatchers.io()){
                val result = block.invoke()
                communications.showProgress(View.GONE)
                result.map(numbersResultMapper)
            }
        }
    }
}