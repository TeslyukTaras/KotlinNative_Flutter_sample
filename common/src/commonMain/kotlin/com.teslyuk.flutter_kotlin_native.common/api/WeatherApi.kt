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
        private const val baseUrl = "https://api.openweathermap.org"
        private var API_KEY = "fcba71b899a98d97f2ac235f742201d8"
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
            takeFrom("$baseUrl/data/2.5/weather?q=${name}&appid=${API_KEY}")
        }
    }.use { response ->
        val json = response.readText()
        logger.log("JSON", json)
        val weather: Weather = JSON.nonstrict.parse(Weather.serializer(),json)
        logger.log("JSON", "deserialized !!!!")
        return@use weather
    }
}













