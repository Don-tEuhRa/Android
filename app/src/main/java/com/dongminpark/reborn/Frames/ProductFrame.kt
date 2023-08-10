package com.dongminpark.reborn.Frames

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.reborn.Buttons.FavoriteButton

@Composable
fun productFrame(
    item: Int,
    navController: NavController,
    route: String,
    name: String = "반팔티셔츠",
    price: Int = 10000,
) {
    Column(
        modifier = Modifier.clickable {
            navController.navigate(route + "Detail")
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(0.dp, Color.Transparent)
                .padding(2.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ImageFormat(url = "")
            Box(modifier = Modifier.padding(8.dp)) {
                FavoriteButton(true)
            }
        }
        Text(
            text = name,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = price.toString(),
            modifier = Modifier.padding(4.dp)
        )
    }
}