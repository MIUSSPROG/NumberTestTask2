package com.example.numbertesttask2.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseTest {

    protected class TestNumberCommunications : NumberCommunications {

        val progressCalledList = mutableListOf<Int>()
        val stateCalledList = mutableListOf<UiState>()
        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Int) {
            progressCalledList.add(show)
        }

        override fun showState(uiState: UiState) {
            stateCalledList.add(uiState)
        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

        override fun observerProgress(owner: LifecycleOwner, observer: Observer<Int>) = Unit

        override fun observerState(owner: LifecycleOwner, observer: Observer<UiState>) = Unit

        override fun observerList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) = Unit
    }
}