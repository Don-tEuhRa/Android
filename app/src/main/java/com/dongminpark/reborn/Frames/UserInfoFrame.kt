package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable

@Composable
fun UserInfoFrame(name: String, phone: String, address: String) {
    // 주문자 정보
    TextFormat(text = "주문자 정보")
    // 주문자 세부 정보(이름 / 전화번호) + 주소
    Row() {
        TextFormat(text = name, size = 12)
        TextFormat(text = phone, size = 12)
    }
    TextFormat(text = address, size = 12)
}