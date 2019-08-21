package com.teslyuk.flutter_kotlin_native_example

import android.os.Bundle
import android.util.Log

import io.flutter.app.FlutterActivity
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall

import com.teslyuk.flutter_kotlin_native_example.dependencies

import com.teslyuk.flutter_kotlin_native.common.*

class MainActivity : FlutterActivity() {

    lateinit var channel: MethodChannel

    lateinit var commonMediator: CommonMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        channel = MethodChannel(flutterView, "/api")
        val dependencyManager = dependencies(this)
        commonMediator = CommonMediator(dependencyManager.coroutineContext, dependencyManager.logger)

        channel.setMethodCallHandler { methodCall, result ->
            commonMediator.processMethodChannel(methodCall.method ?: "",
                    methodCall.arguments ?: emptyList<String>(),
                    { s -> result.success(s) },
                    { e, e1, e2 -> result.error(e, e1, e2) }
            )
        }

        commonMediator.setToUIListener(object : ToUICall {
            override fun onCall(method: String, data: String?) {
                Log.d("MAIN", "reactiveCall onCall")
                Log.d("MAIN", "ANDROID try to invoke method")
                channel.invokeMethod(method, data, object : MethodChannel.Result {
                    override fun notImplemented() {
                        Log.e("MAIN", "not implemented")
                    }

                    override fun error(code: String?, msg: String?, details: Any?) {
                        Log.e("MAIN", "failed: $msg")
                    }

                    override fun success(r: Any?) {
                        Log.e("MAIN", "success: $r")

                    }
                })
            }
        })
    }
}