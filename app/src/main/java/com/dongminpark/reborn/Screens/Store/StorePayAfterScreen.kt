package com.dongminpark.reborn.Screens.Store

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.TextButtonFormat
import com.dongminpark.reborn.Frames.*
import com.dongminpark.reborn.Utils.SpacerHeight

@Composable
fun StorePayAfterScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }

    BackToStore(navController)

    Column() {
        SingleTitleTopAppBarFormat("장바구니")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.SpaceAround
        ){
            item {
                SpacerHeight()
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    TextFormat(text = "주문이 완료되었습니다", size = 28)
                    TextFormat(modifier = Modifier.padding(8.dp), text = "Re:Born과 함께 나눔에 동참해주셔서 감사합니다", size = 16)
                }
            }

            item {
                SpacerHeight()
            }

            item{
                UserInfoFrame(name = "박동민", phone = "010-2245-3683", address = "수원시 장안구 연무동 123-456")
            }

            item {
                SpacerHeight()
            }

            item {
                TextFormat(text = "상품 정보")

                itemList.forEach{item ->
                    ItemInfoFrame(item = item.toString())
                }
            }

            item {
                SpacerHeight()
            }

            item {
                FinalPayPriceFrame(price = 12000, point = 1200)
            }

            item {
                SpacerHeight()
            }

            item {
                // 스토어로 돌아가는 버튼
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    TextButtonFormat(text = "스토어로 돌아가기", widthSize = 120, shape = RoundedCornerShape(8.dp)) {
                        navController.navigate("store")
                    }
                }

            }

            item {
                SpacerHeight()
            }
        }
    }
}

@Composable
fun BackToStore(navController: NavController) {
    BackHandler(enabled = true) {
        navController.navigate("store")
    }
}


@Preview
@Composable
fun StorePayAfterScreenPreview() {
    StorePayAfterScreen(navController = rememberNavController())
}