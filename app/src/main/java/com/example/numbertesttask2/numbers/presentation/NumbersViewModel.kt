package com.example.numbertesttask2.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.numbertesttask2.numbers.domain.NumbersInteractor
import androidx.lifecycle.viewModelScope
import com.example.numbertesttask2.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class NumbersViewModel(
    private val handleResult: HandleNumbersRequest,
    private val manageResources: ManageResources,
    private val communications: NumberCommunications,
    private val interactor: NumbersInteractor,
) : ViewModel(), FetchNumbers, ObserveNumbers, ClearError {

    override fun observerProgress(owner: LifecycleOwner, observer: Observer<Int>) =
        communications.observerProgress(owner, observer)

    override fun observerState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observerState(owner, observer)

    override fun observerList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observerList(owner, observer)


    override fun init(isFirstRun: Boolean) {
        if (isFirstRun){
            handleResult.handle(viewModelScope) {
                interactor.init()
            }
        }
    }

    override fun fetchRandomNumberFact() = handleResult.handle(viewModelScope) {
        interactor.factAboutRandomNumber()
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.ShowError(manageResources.string(R.string.empty_number_error_message)))
        else{
            handleResult.handle(viewModelScope){
                interactor.factAboutNumber(number)
            }
        }
    }

    override fun clearError() {
        communications.showState(UiState.ClearError())
    }
}

interface FetchNumbers{
    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}

interface ClearError{
    fun clearError()
}

interface DispatchersList{

    fun io(): CoroutineDispatcher

    fun ui(): CoroutineDispatcher

    class Base: DispatchersList{
        override fun io(): CoroutineDispatcher = Dispatchers.IO

        override fun ui(): CoroutineDispatcher = Dispatchers.Main
    }
}
