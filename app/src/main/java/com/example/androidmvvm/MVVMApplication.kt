package com.example.androidmvvm

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MVVMApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}