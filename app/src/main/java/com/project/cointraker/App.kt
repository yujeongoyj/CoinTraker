package com.project.cointraker

import android.app.Application
import android.util.Log
import timber.log.Timber


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // Lod.d 외의 방식의 로그 찍을 준비
        Timber.d("onCreate")

    }
}