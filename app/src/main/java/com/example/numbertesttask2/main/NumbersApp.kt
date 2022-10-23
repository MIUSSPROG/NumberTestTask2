package com.example.numbertesttask2.main

import android.app.Application
import com.example.numbertesttask2.BuildConfig
import com.example.numbertesttask2.numbers.data.cloud.CloudModule

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG) CloudModule.Debug() else CloudModule.Release()
    }
}