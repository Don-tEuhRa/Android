package com.dongminpark.reborn.Model

data class OrderCount(
    val allOrderCount: Int,
    val payCount: Int,
    val deliveryCount: Int,
    val deliveredCount: Int,
    val completeCount: Int
)

data class OrderInfo(
    val orderId: Int = -1,
    val status: String = "",
    val phone: String = "",
    val address: String = "",
    val addressDetail: String = "",
    val zipCode: String = " ",
    val paymentDate: String = ""
)