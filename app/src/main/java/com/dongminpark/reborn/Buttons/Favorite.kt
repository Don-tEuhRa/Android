package com.dongminpark.reborn.Buttons

import android.util.Log
import androidx.compose.runtime.Composable
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.Constants.TAG

// 좋아요 버튼
@Composable
fun FavoriteButton() {
    ButtonFormat(icon = R.drawable.heart_outline) {
        Log.e(TAG, "Favorite: 버튼 눌림~", )
        // api 호출
    }
}

// 좋아요 목록
@Composable
fun FavoriteListButton(){
    ButtonFormat(icon = R.drawable.heart_outline) {
        Log.e(TAG, "FavoriteList: 버튼 눌림~", )
        // api 호출
    }
}