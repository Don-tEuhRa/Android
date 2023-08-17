package com.dongminpark.reborn.Buttons


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.R

@Composable
fun CheckBoxButton(isChecked: Boolean, onClick: () -> Unit){
    //var isChecked by remember { mutableStateOf(isChecked) }
    val icon = if (isChecked) R.drawable.checkcircle_solid else R.drawable.checkcircle_outline

    ButtonFormat(modifier = Modifier.padding(12.dp), icon = icon){
        //isChecked = !isChecked
        onClick()
    }
}