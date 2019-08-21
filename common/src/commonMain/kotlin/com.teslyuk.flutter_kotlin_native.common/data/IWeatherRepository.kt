package com.teslyuk.flutter_kotlin_native.common.data

import com.teslyuk.flutter_kotlin_native.common.model.Weather

interface IWeatherRepository {
    suspend fun getWeather(cityName: String): Weather?
}
