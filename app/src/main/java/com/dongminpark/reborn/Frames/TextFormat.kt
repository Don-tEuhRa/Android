package com.dongminpark.reborn.Frames


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TextFormat(text: String, size: Int = 24) {
    Text(
        modifier = Modifier,
        text = text,
        fontSize = size.sp
    )
}