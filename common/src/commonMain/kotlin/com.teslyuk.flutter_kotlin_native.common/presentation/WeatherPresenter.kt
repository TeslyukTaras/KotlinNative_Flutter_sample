package com.teslyuk.flutter_kotlin_native.common.presentation

import com.teslyuk.flutter_kotlin_native.common.data.IWeatherRepository
import com.teslyuk.flutter_kotlin_native.common.model.Weather
import com.teslyuk.flutter_kotlin_native.common.model.WeatherUI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class WeatherPresenter(
        val coroutineContext: CoroutineContext,
        val weatherRepository: IWeatherRepository,
        val view: IWeatherView
) : IWeatherPresenter {

    override fun loadWeatherData(cityName: String) {
        GlobalScope.launch(coroutineContext) {
            try {
                view.hideProgress()
                weatherRepository.getWeather(cityName)?.let { weather ->
                    view.hideProgress()
                    view.showWeather(toWeatherUI(cityName, weather))
                }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }

    private fun toWeatherUI(cityName: String, weather: Weather): WeatherUI {
        return WeatherUI(
                cityName,
                weather.main.pressure,
                weather.main.humidity,
                toCelsius(weather.main.temp),
                toCelsius(weather.main.tempMin),
                toCelsius(weather.main.tempMax)
        )
    }

    private fun toCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }
}