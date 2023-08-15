package com.dongminpark.reborn.Utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun LoadingCircle() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier.wrapContentWidth(),
            color = Point
        )
    }
}