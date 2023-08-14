package com.dongminpark.reborn.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dongminpark.reborn.Navigation.MainScreenView
import com.dongminpark.reborn.navigation.BottomScreen
import com.dongminpark.reborn.ui.theme.Point


// 매개변수로 원하는 페이지를 넣으면 해당 페이지에서 시작. 안넣을 경우 기본은 메인페이지로 설정.
@Composable
fun OnceScreen(
    startDestination: String = BottomScreen.Main.screenRoute
) {
    Box(modifier = Modifier.fillMaxSize().background(Point))
    MainScreenView(startDestination)
}