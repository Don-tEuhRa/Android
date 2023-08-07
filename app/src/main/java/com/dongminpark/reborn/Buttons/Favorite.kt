package com.dongminpark.reborn.Buttons

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.Constants.TAG

// 좋아요 버튼
@Composable
fun FavoriteButton() {
    var isFavorite by remember { mutableStateOf(false) }
    var icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite
    ButtonFormat(icon = icon) {
        Log.e(TAG, "Favorite: 버튼 눌림~", )
        isFavorite = !isFavorite
        // api 호출
    }
}

// 좋아요 목록 이동
@Composable
fun FavoriteListButton(){
    ButtonFormat(icon = R.drawable.heart_outline) {
        Log.e(TAG, "FavoriteList: 버튼 눌림~", )
        // nav 이동
    }
}