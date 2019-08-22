package com.teslyuk.flutter_kotlin_native.common.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherUI(
    val cityName: String,
    val pressure: Long,
    val humidity: Long,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double
)