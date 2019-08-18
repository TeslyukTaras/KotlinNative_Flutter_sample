import UIKit
import Flutter
import common

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {

  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    let controller : FlutterViewController = window?.rootViewController as! FlutterViewController
    let channel = FlutterMethodChannel.init(name: "/api", binaryMessenger: controller)
    let commonMediator = CommonMediator()
    channel.setMethodCallHandler({
        (call: FlutterMethodCall, result: @escaping FlutterResult) -> Void in

        commonMediator.processMethodChannel(method: call.method ,
                                      params: call.arguments ?? [:],
                                      success: { s in result(s); return KotlinUnit() },
                                      error: { _, _, _ in result(FlutterMethodNotImplemented); return KotlinUnit() })

    });
    commonMediator.setToUIListener(toUICall: ToUICallImpl(channel: channel))
    GeneratedPluginRegistrant.register(with: self)
    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
    
    class ToUICallImpl : NSObject, ToUICall {
        
        let channel: FlutterMethodChannel

        init(channel: FlutterMethodChannel) {
            self.channel = channel
        }
        
        func onCall(method: String, data: String?) {
            print("iOS ReactiveCallImpl with %@ data: %@", method, data ?? "nil")
            channel.invokeMethod(method, arguments: data) {
                (result: Any?) -> Void in
                if let error = result as? FlutterError {
                    print("%@ failed: %@", method, error.message!)
                } else if FlutterMethodNotImplemented.isEqual(result) {
                    print("%@ not implemented", method)
                } else {
                    print("%@", result as! NSObject)
                }
            }
        }
    }
}
