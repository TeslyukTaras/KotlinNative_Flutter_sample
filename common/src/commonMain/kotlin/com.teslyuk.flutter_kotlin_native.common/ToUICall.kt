package com.teslyuk.flutter_kotlin_native.common

interface ToUICall {
    fun onCall(method: String, data: String?)
}