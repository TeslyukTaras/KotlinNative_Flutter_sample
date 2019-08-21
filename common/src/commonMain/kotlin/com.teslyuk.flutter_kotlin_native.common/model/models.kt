package com.teslyuk.flutter_kotlin_native.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
        val coord: Coord,
        val weather: List<WeatherElement>,
        val base: String,
        val main: Main,
        val visibility: Long,
        val wind: Wind,
        val clouds: Clouds,
        val dt: Long,
        val sys: Sys,
        val id: Long,
        val name: String,
        val cod: Long
)

@Serializable
data class Clouds(
        val all: Long
)

@Serializable
data class Coord(
        val lon: Double,
        val lat: Double
)

@Serializable
data class Main(
        val temp: Double,
        val pressure: Long,
        val humidity: Long,
        @SerialName("temp_min") val tempMin: Double,
        @SerialName("temp_max") val tempMax: Double
)

@Serializable
data class Sys(
        val type: Long,
        val id: Long,
        val message: Double,
        val country: String,
        val sunrise: Long,
        val sunset: Long
)

@Serializable
data class WeatherElement(
        val id: Long,
        val main: String,
        val description: String,
        val icon: String
)

@Serializable
data class Wind(
        val speed: Double,
        val deg: Long
)
