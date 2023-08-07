package com.dongminpark.reborn.Frames


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextFormat(modifier: Modifier = Modifier, text: String, size: Int = 24, fontWeight: FontWeight = FontWeight.Normal, color: Color = Color.Black) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = size.sp,
        fontWeight = fontWeight,
        color = color
    )
}