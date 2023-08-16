package com.dongminpark.reborn.Utils.GetAddress

import androidx.annotation.Keep

@Keep
class AddressResponse {
    var addresses: Array<AddressData>? = null

    @Keep
    inner class AddressData {
        var roadAddress: String? = null
        var addressElements: Array<AddressElement>? = null

        @Keep
        inner class AddressElement {
            var types: Array<String>? = null
            var longName: String? = null

            override fun toString(): String {
                return "AddressElement{" +
                        "types=" + types?.contentToString() +
                        ", longName='" + longName + '\'' +
                        '}'
            }
        }

        override fun toString(): String {
            return "AddressData{" +
                    "loadAddress='" + roadAddress + '\'' +
                    ", addressElements=" + addressElements?.contentToString() +
                    '}'
        }
    }

    override fun toString(): String {
        var postalCode = ""
        addresses?.getOrNull(0)?.addressElements?.forEach { element ->
            element.types?.forEach { type ->
                if (type == "POSTAL_CODE") {
                    postalCode = element.longName ?: ""
                    return@forEach
                }
            }
            if (postalCode.isNotEmpty()) {
                return@forEach
            }
        }

        return "${addresses?.getOrNull(0)?.roadAddress}\n$postalCode"
    }
}