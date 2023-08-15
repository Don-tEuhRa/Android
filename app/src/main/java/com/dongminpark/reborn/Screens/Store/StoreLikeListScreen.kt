package com.dongminpark.reborn.Screens.Store

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.ReBorn
import com.dongminpark.reborn.Buttons.ShoppingCart
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.Frames.productFrame

val LikedItemList = SnapshotStateList<Int>()

@Composable
fun StoreLikeListScreen(navController: NavController) {
    //val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3,4,5,6,7,8,9,10)) }

    if (LikedItemList.isEmpty()) {
        LikedItemList.addAll(arrayListOf(1, 2, 3,4,5,6,7,8,9,10))
    }

    Column() {
        TopAppBar(
            backgroundColor = Color.White,
            contentPadding = PaddingValues(8.dp)
        ) {
            ReBorn()
            Spacer(modifier = Modifier.weight(0.5f))
            TextFormat(text = "좋아요")
            Spacer(modifier = Modifier.weight(1f))
            ShoppingCart(){ navController.navigate("storeShoppingCart") }
        }
        Divider(color = Color.Black, thickness = 1.dp)
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp),
            columns = GridCells.Fixed(2),
        ) {
            items(LikedItemList) { post ->
                productFrame(post, navController, "community")
            }
        }
    }
}

@Preview
@Composable
fun StoreLikeListScreenPreview() {
    StoreLikeListScreen(navController = rememberNavController())
}