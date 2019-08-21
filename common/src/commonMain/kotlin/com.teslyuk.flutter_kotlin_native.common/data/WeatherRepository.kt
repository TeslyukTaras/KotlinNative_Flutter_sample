package com.teslyuk.flutter_kotlin_native.common.data

import com.teslyuk.flutter_kotlin_native.common.api.WeatherApi
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger
import com.teslyuk.flutter_kotlin_native.common.model.Weather

class WeatherRepository(logger: PlatformLogger) : IWeatherRepository {
    private val api = WeatherApi(logger)

    override suspend fun getWeather(cityName: String): Weather? {
        return api.getWeather(cityName)
    }
}