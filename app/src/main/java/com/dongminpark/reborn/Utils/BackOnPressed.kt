package com.dongminpark.reborn.Utils

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.dongminpark.reborn.Utils.Constants.TAG

@Composable
fun BackOnPressed() {
    val context = LocalContext.current
    var backPressedTime = 0L

    BackHandler(enabled = true) {
        if(System.currentTimeMillis() - backPressedTime <= 1000L) { // 연속 클릭
            (context as Activity).finish() // 앱 종료
        } else {
            Toast.makeText(context, "한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}