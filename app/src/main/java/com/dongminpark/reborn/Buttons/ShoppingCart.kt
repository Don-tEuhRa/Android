package com.dongminpark.reborn.Buttons

import androidx.compose.runtime.Composable
import com.dongminpark.reborn.R

@Composable
fun ShoppingCart(onClick: () -> Unit) {
    ButtonFormat(icon = R.drawable.shopping_outline) {
        onClick()
    }
}