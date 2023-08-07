package com.dongminpark.reborn.Buttons

import android.graphics.drawable.Icon
import android.media.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonFormat(
    modifier: Modifier = Modifier,
    icon: Int,
    size: Int = 24,
    onClick: () -> Unit,
    //content: @Composable () -> Unit
) {
    Icon(
        modifier = modifier
            .size(size.dp)
            .clickable { onClick() },
        painter = painterResource(id = icon),
        contentDescription = "Button",
    )
}

@Composable
fun ButtonFormat(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    size: Int = 24,
    onClick: () -> Unit,
    //content: @Composable () -> Unit
) {
    Icon(
        modifier = modifier
            .size(size.dp)
            .clickable { onClick() },
        imageVector = icon,
        contentDescription = "Button",
    )
}