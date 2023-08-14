package com.dongminpark.reborn.Buttons

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.Constants.TAG

// 좋아요 버튼
@Composable
fun FavoriteButton(isFavorite: Boolean) {
    var isFavorite by rememberSaveable { mutableStateOf(isFavorite) }
    val icon = if (isFavorite) R.drawable.heart_filled else R.drawable.heart_outline

    ButtonFormat(
        icon = icon
    ) {
        isFavorite = !isFavorite
        // api 호출
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