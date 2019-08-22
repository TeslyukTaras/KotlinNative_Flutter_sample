import 'dart:convert';

Weather weatherFromJson(String str) => Weather.fromJson(json.decode(str));

String weatherToJson(Weather data) => json.encode(data.toJson());

class Weather {
  String cityName;
  int pressure;
  int humidity;
  double temp;
  double tempMin;
  double tempMax;

  Weather({
    this.cityName,
    this.pressure,
    this.humidity,
    this.temp,
    this.tempMin,
    this.tempMax,
  });

  factory Weather.fromJson(Map<String, dynamic> json) => new Weather(
    cityName: json["cityName"],
    pressure: json["pressure"],
    humidity: json["humidity"],
    temp: json["temp"].toDouble(),
    tempMin: json["tempMin"].toDouble(),
    tempMax: json["tempMax"].toDouble(),
  );

  Map<String, dynamic> toJson() => {
    "cityName": cityName,
    "pressure": pressure,
    "humidity": humidity,
    "temp": temp,
    "tempMin": tempMin,
    "tempMax": tempMax,
  };
}
