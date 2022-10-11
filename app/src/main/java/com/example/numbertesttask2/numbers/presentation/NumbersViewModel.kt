package com.example.numbertesttask2.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.numbertesttask2.numbers.domain.NumbersInteractor
import androidx.lifecycle.viewModelScope
import com.example.numbertesttask2.R
import com.example.numbertesttask2.numbers.domain.NumbersResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val dispatchers: DispatchersList,
    private val manageResources: ManageResources,
    private val communications: NumberCommunications,
    private val interactor: NumbersInteractor,
    private val numbersResultMapper: NumbersResult.Mapper<Unit>
) : ViewModel(), FetchNumbers, ObserveNumbers {

    override fun observerProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observerProgress(owner, observer)

    override fun observerState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observerState(owner, observer)

    override fun observerList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observerList(owner, observer)

    private fun doAsync(block: suspend () -> NumbersResult){
        viewModelScope.launch(dispatchers.io()){
            val result = block.invoke()
            communications.showProgress(false)
            result.map(numbersResultMapper)
        }
    }

    override fun init(isFirstRun: Boolean) {
        communications.showProgress(true)
        doAsync { interactor.init() }
    }

    override fun fetchRandomNumberFact() {
        communications.showProgress(true)
        doAsync { interactor.factAboutRandomNumber() }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        else{
            communications.showProgress(true)
            doAsync { interactor.factAboutNumber(number) }
        }
    }
}

interface FetchNumbers{
    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}

interface DispatchersList{

    fun io(): CoroutineDispatcher

    fun ui(): CoroutineDispatcher

    class Base: DispatchersList{
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun ui(): CoroutineDispatcher = Dispatchers.Main
    }
}
