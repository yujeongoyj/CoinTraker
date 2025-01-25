package com.project.cointraker

import android.app.Application
import android.content.Context
import android.util.Log
import timber.log.Timber


class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance : App? = null
        fun context() : Context {
            return instance!!.applicationContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // Lod.d 외의 방식의 로그 찍을 준비
        Timber.d("onCreate")

    }
}