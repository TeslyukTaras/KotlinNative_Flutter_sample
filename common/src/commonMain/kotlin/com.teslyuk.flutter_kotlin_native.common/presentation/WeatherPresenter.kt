package com.teslyuk.flutter_kotlin_native.common.presentation

import com.teslyuk.flutter_kotlin_native.common.data.IWeatherRepository
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
                    view.showWeather(weather)
                }
            } catch (e: Exception) {
                view.showError()
            }
        }
    }
}