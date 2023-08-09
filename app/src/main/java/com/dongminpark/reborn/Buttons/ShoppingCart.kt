package com.dongminpark.reborn.Buttons

import android.util.Log
import androidx.compose.runtime.Composable
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.Constants.TAG

@Composable
fun ShoppingCart(onClick: () -> Unit) {
    ButtonFormat(icon = R.drawable.shopping_outline) {
        Log.e(TAG, "ShoppoingCart: 버튼 눌림~", )
        onClick()
    }
}