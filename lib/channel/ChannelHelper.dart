import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:flutter_kotlin_native_example/IWeatherView.dart';
import 'package:flutter_kotlin_native_example/model/Weather.dart';

class ChannelHelper {
  static const CHANNEL_NAME = '/api';
  static const _channel = MethodChannel(CHANNEL_NAME);

  IWeatherView view;

  start(IWeatherView view) {
    _channel.setMethodCallHandler((MethodCall call) async {
      print('UI received call from ${call.method}');
      print('UI received call with argument ${call.arguments}');
      switch (call.method) {
        case 'weather+data':
          {
            Map<String, dynamic> weatherJson = jsonDecode(call.arguments);
            Weather weather = Weather.fromJson(weatherJson);
            view.showWeather(weather);
            return 'success';
          }
        case 'weather+show_dialog':
          {
            view.showProgress();
            return 'success';
          }
        case 'weather+hide_dialog':
          {
            view.hideProgress();
            return 'success';
          }
        case 'weather+error':
          {
            view.showError();
            return 'success';
          }
        default:
          return 'fail to find method';
      }
    });
  }

  void getWeather(String cityName) async {
    var callResult = await _channel.invokeMethod('get weather', cityName);
  }
}