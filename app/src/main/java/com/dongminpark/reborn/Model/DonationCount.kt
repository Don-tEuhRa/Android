package com.dongminpark.reborn.Model

data class DonationCount(
    val receiptCount: Int,
    val pickupCount: Int,
    val reformCount: Int,
    val arriveCount: Int,
    val productCount: Int,
    val donationCount: Int
)

data class DonationInfo(
    val receiptStatus: String ="",
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val pickUpDate: String = "",
    val productId: Int = -1,
    val productName: String = "",
    val price: Int = -1,
    val date: String = ""
)
