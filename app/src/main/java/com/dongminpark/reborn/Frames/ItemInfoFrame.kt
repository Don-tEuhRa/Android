package com.dongminpark.reborn.Frames

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.reborn.Model.Product

@Composable
fun ItemInfoFrame(item: Product, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate("storeDetail/${item.productId}")
            }
            .padding(horizontal = 0.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // item 이미지
        ImageFormat(modifier = Modifier.padding(12.dp), url = item.thumbnailUrl, size = 80)

        // item 이름, 가격
        Column(modifier = Modifier.padding(8.dp)) {
            TextFormat(text = item.title, size = 12)
            TextFormat(text = "${item.price}원", size = 16)
        }
    }
}