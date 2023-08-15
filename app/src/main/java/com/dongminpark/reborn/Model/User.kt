package com.dongminpark.reborn.Model

data class User(
    val userId: Int = -1,
    val nickname: String = "",
    val email: String = "",
    val role: String = "",
    val address: String = "",
    val detailAddress: String = "",
    val zipCode: Int = -1,
    val phone: String = "",
    val point: Int = 0
)

data class MypageUser(
    val userId: Int = -1,
    val name: String = "",
    val point: Int = 0,
    val donationPoint: Int = 0,
    val donationCount: Int = 0
)
