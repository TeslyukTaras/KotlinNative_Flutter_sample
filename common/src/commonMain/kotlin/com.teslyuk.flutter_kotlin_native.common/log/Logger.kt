package com.teslyuk.flutter_kotlin_native.common.log

interface PlatformLogger {

    fun logException(tag: String, text: String, exception: Throwable?) {

    }

    fun log(tag: String, text: String) {

    }
}