package com.dongminpark.reborn.Buttons


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.R

@Composable
fun CheckBoxButton(onClick: () -> Unit){
    var isChecked by remember { mutableStateOf(false) }
    val icon = if (isChecked) R.drawable.checkcircle_solid else R.drawable.checkcircle_outline

    ButtonFormat(modifier = Modifier.padding(12.dp), icon = icon){
        isChecked = !isChecked
        onClick()
    }
}

// Icons.Outlined.CheckCircle - 빈 체크박스
// Icons.Filled.CheckCircle - 체크된 체크박스