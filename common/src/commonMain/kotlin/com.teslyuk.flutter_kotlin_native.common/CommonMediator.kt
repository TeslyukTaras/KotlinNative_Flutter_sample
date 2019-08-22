package com.teslyuk.flutter_kotlin_native.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.teslyuk.flutter_kotlin_native.common.data.IWeatherRepository
import com.teslyuk.flutter_kotlin_native.common.data.WeatherRepository
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger
import com.teslyuk.flutter_kotlin_native.common.presentation.IWeatherPresenter
import com.teslyuk.flutter_kotlin_native.common.presentation.IWeatherView
import com.teslyuk.flutter_kotlin_native.common.presentation.WeatherPresenter
import com.teslyuk.flutter_kotlin_native.common.presentation.WeatherView

class CommonMediator(override val coroutineContext: CoroutineContext, val logger: PlatformLogger) : CoroutineScope {

    private var toUICall: ToUICall? = null

    private var weatherRepository: IWeatherRepository
    private var weatherPresenter: IWeatherPresenter
    private var weatherView: IWeatherView

    init {
        weatherView = WeatherView(this)
        weatherRepository = WeatherRepository(logger)
        weatherPresenter = WeatherPresenter(coroutineContext, weatherRepository, weatherView)
    }

    fun processMethodChannel(
            method: String,
            params: Any,
            success: (Any) -> Unit,
            error: (String?, String?, String?) -> Unit
    ) {
        logger.log("MAIN", "processMethodChannel : $method")
        if (method == "platformName") {
            var name = platformName()
            success(name)
        } else if (method == "get weather") {
            getWeather(params.toString())
        } else {
            error("unknown method", "unknown method", "unknown method")
        }
    }

    private fun getWeather(cityName: String) {
        logger.log("MAIN", "weatherPresenter.loadWeatherData : $cityName")
        weatherPresenter.loadWeatherData(cityName)
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