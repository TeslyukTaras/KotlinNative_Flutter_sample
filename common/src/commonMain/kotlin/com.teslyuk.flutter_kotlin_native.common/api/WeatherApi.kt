package com.teslyuk.flutter_kotlin_native.common.api

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.teslyuk.flutter_kotlin_native.common.log.PlatformLogger
import com.teslyuk.flutter_kotlin_native.common.model.Weather
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import kotlinx.io.core.use
import kotlinx.serialization.json.JSON

class WeatherApi(val logger: PlatformLogger) {

    companion object {
        private const val baseUrl = "https://samples.openweathermap.org"
    }

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = JsonKotlinxSerializer().apply {
                setMapper<Weather>(Weather.serializer())
            }
        }
        install(ExpectSuccess)
    }

    suspend fun getWeather(name: String): Weather = client.request<HttpResponse> {
        method = HttpMethod.Get
        url {
            takeFrom("$baseUrl/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22")
        }
    }.use { response ->
        val json = response.readText()
        logger.log("JSON", json)
        val weather: Weather = JSON.nonstrict.parse(Weather.serializer(),json)
        logger.log("JSON", "deserialized !!!!")
        return@use weather
    }
}













