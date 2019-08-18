package com.teslyuk.flutter_kotlin_native.common

import platform.UIKit.UIDevice
import platform.darwin.*

actual fun platformName(): String {
    return UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion
}