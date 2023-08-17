package com.dongminpark.reborn.Buttons

import android.util.Log
import androidx.compose.runtime.*
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.Constants.TAG

@Composable
fun FavoriteButton(isFavorite: Boolean, onClick: () -> Unit) {
    val icon = if (isFavorite) R.drawable.heart_filled else R.drawable.heart_outline

    ButtonFormat(
        icon = icon
    ) {
        onClick()
    }
}

// 좋아요 목록 이동
@Composable
fun FavoriteListButton(onClick: () -> Unit){
    ButtonFormat(icon = R.drawable.heart_outline) {
        Log.e(TAG, "FavoriteList: 버튼 눌림~", )
        onClick()
    }
}