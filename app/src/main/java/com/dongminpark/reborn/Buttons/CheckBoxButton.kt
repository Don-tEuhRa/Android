package com.dongminpark.reborn.Buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxButton(onClick: () -> Unit){
    Icon(
        //Icons.Filled.CheckCircle,
        Icons.Outlined.CheckCircle,
        contentDescription = "check box",
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(12.dp)
    )
}

// Icons.Outlined.CheckCircle - 빈 체크박스
// Icons.Filled.CheckCircle - 체크된 체크박스