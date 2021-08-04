package com.example.newsapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {




}

fun enableLogging() = BuildConfig.BUILD_TYPE != "release"