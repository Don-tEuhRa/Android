package com.dongminpark.reborn.Screens.Store

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Buttons.ReBorn
import com.dongminpark.reborn.Buttons.ShoppingCart
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.Frames.productFrame
import com.dongminpark.reborn.Model.Product
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Screens.progressBar
import com.dongminpark.reborn.Utils.Constants
import com.dongminpark.reborn.Utils.LoadingCircle
import com.dongminpark.reborn.Utils.MESSAGE
import com.dongminpark.reborn.Utils.RESPONSE_STATE

var LikedItemList = SnapshotStateList<Product>()

@Composable
fun StoreLikeListScreen(navController: NavController) {
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    if (isLoading){
        // api 호출
        LoadingCircle()
        RetrofitManager.instance.interestList(
            completion = { responseState, list ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(Constants.TAG, "api 호출 성공")
                        LikedItemList.clear()
                        LikedItemList.addAll(list!!)
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "api 호출 에러")
                    }
                }
            })
    }else{
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
                columns = GridCells.Fixed(3),
            ) {
                items(LikedItemList) { product ->
                    Box(modifier = Modifier.padding(4.dp)) {
                        productFrame(product, navController)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StoreLikeListScreenPreview() {
    StoreLikeListScreen(navController = rememberNavController())
}