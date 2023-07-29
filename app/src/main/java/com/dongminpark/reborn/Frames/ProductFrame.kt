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
//import com.dongminpark.projectgd.Model.Post

@Composable
fun productFrame(
    item: Int,
    navController: NavController,
    route: String,
    bookmark: Boolean,
    isMe: Boolean,
    userIsMe: Boolean,
    name: String = "반팔티셔츠",
    price: Int = 10000,
) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(0.dp, Color.Transparent)
                .clickable {
                    //navController.navigate(route + "_detail_screen/${post.postNum}")
                }
                .padding(2.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            ImageFormat(url = "")
            if (userIsMe && isMe) {
                Box(modifier = Modifier.padding(8.dp)) {
                    FavoriteButton()
                }
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