package com.dongminpark.reborn.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dongminpark.reborn.Navigation.MainScreenView
import com.dongminpark.reborn.navigation.BottomScreen
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun OnceScreen(
    startDestination: String = BottomScreen.Main.screenRoute
) {
    Box(modifier = Modifier.fillMaxSize().background(Point))
    MainScreenView(startDestination)
}