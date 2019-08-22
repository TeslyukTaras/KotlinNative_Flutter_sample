import 'package:flutter/material.dart';
import 'package:flutter_kotlin_native_example/IWeatherView.dart';
import 'package:flutter_kotlin_native_example/channel/ChannelHelper.dart';

import 'model/Weather.dart';
import 'package:flutter_kotlin_native_example/const.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      home: MyHomePage(title: 'Kotlin/Native - Flutter Demo'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> implements IWeatherView {
  ChannelHelper channelHelper = ChannelHelper();

  String cityName = 'Lviv';
  bool isProgress = false;
  Weather weather = Weather();

  @override
  void initState() {
    super.initState();
    channelHelper.start(this);
    channelHelper.getWeather(cityName);
  }

  @override
  void hideProgress() {
    setState(() {
      isProgress = false;
    });
  }

  @override
  void showError() {
    showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text("Failure"),
            content: Text("Failed to load weather data"),
            actions: <Widget>[
              FlatButton(
                child: Text("Close"),
                onPressed: () {
                  Navigator.of(context).pop();
                  hideProgress();
                },
              )
            ],
          );
        });
  }

  @override
  void showProgress() {
    setState(() {
      isProgress = true;
    });
  }

  @override
  void showWeather(Weather weather) {
    setState(() {
      this.weather = weather;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage('images/background.png'),
            fit: BoxFit.cover,
            colorFilter: ColorFilter.mode(
                Colors.white.withOpacity(0.8), BlendMode.dstATop),
          ),
        ),
//        color: Colors.blue,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            getSearchField(),
            getProgress(),
            getMainDataContainer(),
          ],
        ),
      ),
    );
  }

  Widget getSearchField() {
    return Container(
      padding: EdgeInsets.fromLTRB(20.0, 20.0, 20.0, 0.0),
      child: TextField(
        onChanged: (text) {
          cityName = text;
        },
        onSubmitted: (text) {
          channelHelper.getWeather(cityName);
        },
        decoration: InputDecoration(
            filled: true,
            fillColor: Colors.white,
            icon: Icon(
              Icons.location_city,
              color: Colors.white,
            ),
            hintText: 'Enter City Name',
            hintStyle: TextStyle(color: Colors.grey),
            border:
                OutlineInputBorder(borderRadius: BorderRadius.circular(10.0))),
      ),
    );
  }

  Widget getProgress() {
    return Visibility(
      visible: isProgress,
      child: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            CircularProgressIndicator(
              backgroundColor: Colors.white,
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                "Loading",
                style: kTextSmallStyle,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget getMainDataContainer() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
          padding: EdgeInsets.fromLTRB(20.0, 0.0, 20.0, 20.0),
          child: Text(
            '$cityName',
            style: kTextBigStyle,
          ),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Row(
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.fromLTRB(20.0, 0.0, 0.0, 0.0),
                  child: Icon(
                    Icons.keyboard_arrow_up,
                    color: Colors.white,
                  ),
                ),
                Padding(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 20.0, 0.0),
                  child: Text(
                    '${roundValue(weather.tempMax)}*C',
                    style: kTextSmallStyle,
                  ),
                )
              ],
            ),
            Row(
              children: <Widget>[
                Icon(
                  Icons.keyboard_arrow_down,
                  color: Colors.white,
                ),
                Padding(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 20.0, 0.0),
                  child: Text(
                    '${roundValue(weather.tempMin)}*C',
                    style: kTextSmallStyle,
                  ),
                )
              ],
            ),
            Row(
              children: <Widget>[
                Icon(
                  Icons.adjust,
                  color: Colors.white,
                ),
                Padding(
                  padding: EdgeInsets.fromLTRB(8.0, 0.0, 20.0, 0.0),
                  child: Text(
                    '${weather.pressure}KP',
                    style: kTextSmallStyle,
                  ),
                )
              ],
            ),
          ],
        ),
        Padding(
          padding: const EdgeInsets.all(20.0),
          child: Text(
            '${roundValue(weather.temp)}*C',
            style: kTextBigStyle,
          ),
        ),
      ],
    );
  }

  String roundValue(double value) {
    if (value == null)
      return '0';
    else {
      return value.toStringAsFixed(0);
    }
  }
}
