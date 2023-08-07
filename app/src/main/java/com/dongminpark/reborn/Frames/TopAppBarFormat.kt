package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.Buttons.ReBorn

@Composable
fun SingleTitleTopAppBarFormat(title: String) {
    TopAppBar(
        backgroundColor = Color.White,
        contentPadding = PaddingValues(8.dp)
    ) {
        ReBorn()
        Spacer(modifier = Modifier.fillMaxWidth(0.3f))
        TextFormat(text = title)
    }
    Divider(color = Color.Black, thickness = 1.dp)
}