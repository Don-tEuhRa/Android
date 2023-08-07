package com.dongminpark.reborn.Buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.Frames.TextFormat

@Composable
fun TextButtonFormat(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = CircleShape,
    heightSize: Int = 40,
    widthSize: Int = 100,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .size(width = widthSize.dp, height = heightSize.dp)
            .clip(shape)
            .background(Color.Red, shape = shape)
            .border(1.dp, Color.Black, shape = shape),
        onClick = { onClick() }
    ) {
        TextFormat(text = text, size = 12)
    }
}

@Composable
fun LongTextButtonFormat(count: Int, price: String, onClick: () -> Unit) { // 매개변수 text값, onClick매서드
    TextButton(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(Color.Red, CircleShape)
            .border(1.dp, Color.Black, CircleShape)
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFormat(text = "총 ${count}개 ", size = 16)

            TextFormat(text = "${price}원 결제", size = 16)
        }
    }
}