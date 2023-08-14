package com.dongminpark.reborn.Frames

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ItemInfoFrame(item: String, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("storeDetail")
            }
            .padding(horizontal = 0.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // item 이미지
        ImageFormat(modifier = Modifier.padding(12.dp), url = "testurl", size = 80) // item.url

        // item 이름, 가격
        Column(modifier = Modifier.padding(8.dp)) {
            TextFormat(text = "반팔티셔츠", size = 12) // item.name
            TextFormat(text = "10000원", size = 16) // item.price
        }
    }
}