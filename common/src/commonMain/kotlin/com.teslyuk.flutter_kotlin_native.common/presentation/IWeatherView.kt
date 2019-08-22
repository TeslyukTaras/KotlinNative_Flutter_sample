package com.teslyuk.flutter_kotlin_native.common.presentation

import com.teslyuk.flutter_kotlin_native.common.model.WeatherUI

interface IWeatherView {
    fun showWeather(weather: WeatherUI)
    fun showProgress()
    fun hideProgress()
    fun showError()
}