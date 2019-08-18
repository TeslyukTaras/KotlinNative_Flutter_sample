package com.teslyuk.flutter_kotlin_native.common

class CommonMediator {

    private var toUICall: ToUICall? = null

    fun processMethodChannel(method: String,
                             params: Any,
                             success: (Any) -> Unit,
                             error: (String?, String?, String?) -> Unit) {
        if (method == "platformName") {
            var name = platformName()
            success(name)
        } else if (method == "call me") {
            if (toUICall != null) {
                toUICall!!.onCall("ping", null)
            }
        } else {
            error("unknown method", "unknown method", "unknown method")
        }
    }

    fun setToUIListener(toUICall: ToUICall?) {
        this.toUICall = toUICall
    }
}