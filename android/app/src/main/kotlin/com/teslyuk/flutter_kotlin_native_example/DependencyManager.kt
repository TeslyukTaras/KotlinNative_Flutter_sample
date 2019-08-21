package com.teslyuk.flutter_kotlin_native_example

import android.app.Application
import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger

class DependencyManager(private val application: Application) {

    val coroutineContext by lazy { Dispatchers.Main }

    val logger: PlatformLogger by lazy {
        object : PlatformLogger {
            override fun logException(tag: String, text: String, exception: Throwable?) {
                Log.e(tag, text, exception)
            }

            override fun log(tag: String, text: String) {
                Log.d(tag, text)
            }
        }
    }
}

fun dependencies(context: Context): DependencyManager {
    val app = context.applicationContext
    val kotlinConfApplication = app as MainApplication
    return kotlinConfApplication.dependencyManager
}