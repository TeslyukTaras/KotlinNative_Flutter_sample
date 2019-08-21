package com.teslyuk.flutter_kotlin_native.common

import com.teslyuk.flutter_kotlin_native.common.data.IWeatherRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.teslyuk.flutter_kotlin_native.common.data.WeatherRepository
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger
import com.teslyuk.flutter_kotlin_native.common.model.Weather
import com.teslyuk.flutter_kotlin_native.common.presentation.IWeatherPresenter
import com.teslyuk.flutter_kotlin_native.common.presentation.IWeatherView
import com.teslyuk.flutter_kotlin_native.common.presentation.WeatherPresenter

class CommonMediator(override val coroutineContext: CoroutineContext, val logger: PlatformLogger) : CoroutineScope {

    companion object {
        const val WEATHER_ROOT = "weather+"
        const val DATA = "data"
        const val SHOW_DIALOG = "show_dialog"
        const val HIDE_DIALOG = "hide_dialog"
        const val ERROR = "error"
    }

    private var toUICall: ToUICall? = null

    private var weatherRepository: IWeatherRepository
    private var weatherPresenter: IWeatherPresenter
    private var weatherView: IWeatherView = object : IWeatherView {
        override fun showWeather(weather: Weather) {
            sendBack("$WEATHER_ROOT$DATA", weather.toString())
        }

        override fun showProgress() {
            sendBack("$WEATHER_ROOT$SHOW_DIALOG", null)
        }

        override fun hideProgress() {
            sendBack("$WEATHER_ROOT$HIDE_DIALOG", null)
        }

        override fun showError() {
            sendBack("$WEATHER_ROOT$ERROR", null)
        }
    }

    init {
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