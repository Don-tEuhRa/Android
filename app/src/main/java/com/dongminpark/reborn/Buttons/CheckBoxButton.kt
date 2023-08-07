package com.dongminpark.reborn.Buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxButton(onClick: () -> Unit){
    var isChecked by remember { mutableStateOf(false) }
    var icon = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle

    ButtonFormat(modifier = Modifier.padding(12.dp), icon = icon){
        isChecked = !isChecked
        onClick()
    }
}

// Icons.Outlined.CheckCircle - 빈 체크박스
// Icons.Filled.CheckCircle - 체크된 체크박스