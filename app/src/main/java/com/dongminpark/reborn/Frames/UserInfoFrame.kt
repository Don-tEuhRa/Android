package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserInfoFrame(name: String, phone: String, address: String) {
    // 주문자 정보
    TextFormat(text = "주문자 정보")
    // 주문자 세부 정보(이름 / 전화번호) + 주소
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row() {
            TextFormat(text = name, size = 12)
            Spacer(modifier = Modifier.width(8.dp))
            TextFormat(text = phone, size = 12)
        }
        TextFormat(text = address, size = 12)
    }
}