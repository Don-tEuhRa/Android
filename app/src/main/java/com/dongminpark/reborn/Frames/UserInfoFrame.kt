package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserInfoFrame(name: String, phone: String, address: String) {
    TextFormat(text = "주문자 정보")

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        TextFormat(text = name, size = 16)
        TextFormat(text = phone, size = 12)
        TextFormat(text = address, size = 12)
    }
}