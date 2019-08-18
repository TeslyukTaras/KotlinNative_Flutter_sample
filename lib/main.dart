import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  static const CHANNEL_NAME = '/api';
  static const _channel = MethodChannel(CHANNEL_NAME);
  var _platform = "?";
  var pingPongCount = 0;

  @override
  void initState() {
    super.initState();
    print('DART initState setMethodCallHandler');
    _channel.setMethodCallHandler((MethodCall call) async{
      print('MAIN received call from ${call.method}');
      switch(call.method) {
        case 'ping': {
          setState(() {
            pingPongCount++;
          });
          return 'pong';
        }
        default: return 'fail to find method';
      }
    });
  }

  void _sendTestCall() async {
    var callResult = await _channel.invokeMethod('call me');
    print('DART callResult ${callResult}');
  }

  void _incrementCounter() async {
    var platform = await _channel.invokeMethod('platformName');

    setState(() {
      _counter++;
      _platform = platform;
    });
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            FlatButton(child: Text("CLick me $pingPongCount"),onPressed: () {
              _sendTestCall();
            },),
            Text(
              'You have pushed the button this many times on $_platform',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.display1,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
