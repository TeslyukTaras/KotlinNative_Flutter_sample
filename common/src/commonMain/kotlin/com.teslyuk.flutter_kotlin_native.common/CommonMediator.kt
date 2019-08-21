package com.teslyuk.flutter_kotlin_native.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.teslyuk.flutter_kotlin_native.common.data.WeatherRepository
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger

class CommonMediator(override val coroutineContext: CoroutineContext, val logger: PlatformLogger) : CoroutineScope {

    private var toUICall: ToUICall? = null

    fun processMethodChannel(method: String,
                             params: Any,
                             success: (Any) -> Unit,
                             error: (String?, String?, String?) -> Unit) {
        if (method == "platformName") {
            var name = platformName()
            success(name)
        } else if (method == "call me") {
            getWeather("London,uk")
        } else {
            error("unknown method", "unknown method", "unknown method")
        }
    }

    private fun getWeather(name: String) {
        val repository = WeatherRepository(logger)
        GlobalScope.launch(coroutineContext) {
            try {
                repository.getWeather(name)?.let { weather ->
                    sendBack("ping", "$weather")
                }
            } catch (e: Exception) {
                sendBack("ping", "$e")
            }
        }
    }

    fun sendBack(method: String, data: String?) {
        if (toUICall != null) {
            toUICall!!.onCall(method, data)
        }
    }

    fun setToUIListener(toUICall: ToUICall?) {
        this.toUICall = toUICall
    }
}