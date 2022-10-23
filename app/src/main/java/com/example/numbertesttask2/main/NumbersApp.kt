package com.example.numbertesttask2.main

import android.app.Application
import com.example.numbertesttask2.BuildConfig
import com.example.numbertesttask2.numbers.data.CloudModule
import com.example.numbertesttask2.numbers.data.NumbersCloudDataSource
import com.example.numbertesttask2.numbers.data.NumbersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG) CloudModule.Debug() else CloudModule.Release()
    }
}