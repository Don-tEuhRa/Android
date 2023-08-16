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
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun TextButtonFormat(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape = CircleShape,
    heightSize: Int = 40,
    widthSize: Int = 100,
    backgroundColor: Color = Color.Red,
    borderColor: Color = Color.Black,
    textColor: Color = Color.Black,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .size(width = widthSize.dp, height = heightSize.dp)
            .clip(shape)
            .background(backgroundColor, shape = shape)
            .border(1.dp, borderColor, shape = shape),
        onClick = { onClick() }
    ) {
        TextFormat(text = text, size = 12, color = textColor)
    }
}

@Composable
fun LongTextButtonFormat(
    count: Int,
    price: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) { // 매개변수 text값, onClick매서드
    TextButton(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(if (enabled) Point else Color.LightGray, CircleShape)
            .fillMaxWidth(),
        enabled = enabled,
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