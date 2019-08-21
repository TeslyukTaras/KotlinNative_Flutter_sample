package com.teslyuk.flutter_kotlin_native_example

import android.app.*
import android.content.*
import io.flutter.app.FlutterApplication

class MainApplication : FlutterApplication() {

    val dependencyManager: DependencyManager by lazy { DependencyManager(this) }

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            println(throwable)
            throwable.printStackTrace()
            throwable?.cause?.printStackTrace()
        }
    }
}