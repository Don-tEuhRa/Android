package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowSpaceBetweenFrame(modifier: Modifier = Modifier, first: String, second: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextFormat(text = first, size = 12)
        TextFormat(text = second, size = 12)
    }
}