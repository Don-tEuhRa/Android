package com.dongminpark.reborn.Frames

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemInfoFrame(itemList: MutableList<Int>) {
    // 상품 정보
    TextFormat(text = "상품 정보")

    itemList.forEach { item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // item 이미지
            ImageFormat(modifier = Modifier.padding(12.dp), url = "testurl", size = 80)

            // item 이름, 가격
            Column(modifier = Modifier.padding(8.dp)) {
                TextFormat(text = "반팔티셔츠", size = 12) // item.name
                TextFormat(text = "10000원", size = 16) // item.price
            }
        }
    }
}