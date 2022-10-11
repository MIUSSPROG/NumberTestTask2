package com.example.numbertesttask2.numbers.presentation

import com.example.numbertesttask2.numbers.domain.NumberFact

sealed class UiState{

//    interface Mapper<T> {
//        fun map(message: String): T
//    }
//
//    abstract fun <T> map(mapper: Mapper<T>): T
//
//    class Success: UiState(){
//        override fun <T> map(mapper: Mapper<T>): T = mapper.map("")
//    }
//
//    class Error(private val message: String): UiState(){
//        override fun <T> map(mapper: Mapper<T>): T = mapper.map(message)
//    }

    class Success: UiState(){
        override fun equals(other: Any?): Boolean {
            return if (other is Success) true else super.equals(other)
        }
    }

    data class Error(private val message: String): UiState(){}
}
