package com.example.numbertesttask2.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface NumberCommunications : ObserveNumbers {

    fun showProgress(show: Boolean)

    fun showState(uiState: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val progress: ProgressCommunication,
        private val state: NumberStateCommunication,
        private val numbersList: NumbersLisCommunication
    ): NumberCommunications{
        override fun showProgress(show: Boolean) = progress.map(show)

        override fun showState(uiState: UiState) = state.map(uiState)

        override fun showList(list: List<NumberUi>) = numbersList.map(list)

        override fun observerProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
            progress.observe(owner, observer)

        override fun observerState(owner: LifecycleOwner, observer: Observer<UiState>) =
            state.observe(owner, observer)

        override fun observerList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
            numbersList.observe(owner, observer)
        }
    }
}

interface ObserveNumbers{

    fun observerProgress(owner: LifecycleOwner, observer: Observer<Boolean>)
    fun observerState(owner: LifecycleOwner, observer: Observer<UiState>)
    fun observerList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>)
}

interface ProgressCommunication: Communication.Mutable<Boolean>{
    class Base: Communication.Post<Boolean>(), ProgressCommunication
}

interface NumberStateCommunication: Communication.Mutable<UiState>{
    class Base: Communication.Post<UiState>(), NumberStateCommunication
}

interface NumbersLisCommunication: Communication.Mutable<List<NumberUi>>{
    class Base: Communication.Post<List<NumberUi>>(), NumbersLisCommunication
}