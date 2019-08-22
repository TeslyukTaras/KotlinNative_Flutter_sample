package com.teslyuk.flutter_kotlin_native.common.presentation

import com.teslyuk.flutter_kotlin_native.common.model.WeatherUI
import com.teslyuk.flutter_kotlin_native.common.CommonMediator

import kotlinx.serialization.json.JSON

class WeatherView(val mediator: CommonMediator) : IWeatherView {

    companion object {
        const val WEATHER_ROOT = "weather+"
        const val DATA = "data"
        const val SHOW_DIALOG = "show_dialog"
        const val HIDE_DIALOG = "hide_dialog"
        const val ERROR = "error"
    }

    override fun showWeather(weather: WeatherUI) {
        val weatherJson = JSON.stringify(WeatherUI.serializer(), weather)
        mediator.sendBack("$WEATHER_ROOT$DATA", weatherJson)
    }

    override fun showProgress() {
        mediator.sendBack("$WEATHER_ROOT$SHOW_DIALOG", null)
    }

    override fun hideProgress() {
        mediator.sendBack("$WEATHER_ROOT$HIDE_DIALOG", null)
    }

    override fun showError() {
        mediator.sendBack("$WEATHER_ROOT$ERROR", null)
    }
}