package com.dongminpark.reborn.Buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonFormat(
    icon: Int,
    size: Int = 24,
    onClick: () -> Unit,
    //content: @Composable () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(size.dp)
            .clickable { onClick() },
        painter = painterResource(id = icon),
        contentDescription = "Button",
    )
}