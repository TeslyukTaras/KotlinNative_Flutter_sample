import 'package:flutter_kotlin_native_example/model/Weather.dart';

abstract class IWeatherView {
  void showWeather(Weather weather);

  void showProgress();

  void hideProgress();

  void showError();
}