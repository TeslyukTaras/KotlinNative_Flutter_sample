package com.teslyuk.flutter_kotlin_native.common.presentation

import com.teslyuk.flutter_kotlin_native.common.model.Weather

interface IWeatherView {
    fun showWeather(weather: Weather)
    fun showProgress()
    fun hideProgress()
    fun showError()
}