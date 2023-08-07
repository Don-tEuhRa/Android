package com.dongminpark.reborn.Buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClearTextButton(onClick: () -> Unit) {
    ButtonFormat(
        modifier = Modifier.padding(vertical = 15.dp, horizontal = 10.dp),
        icon = Icons.Default.Clear
    ) {
        onClick()
    }
}