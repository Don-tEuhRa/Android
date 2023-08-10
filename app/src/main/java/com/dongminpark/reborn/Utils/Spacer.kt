package com.dongminpark.reborn.Utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHeight(height: Int = 24) {
    Spacer(modifier = Modifier.height(height.dp))
}