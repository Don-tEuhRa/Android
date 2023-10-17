package com.dongminpark.reborn.Screens.Store

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.reborn.Buttons.TextButtonFormat
import com.dongminpark.reborn.Frames.*
import com.dongminpark.reborn.Utils.SpacerHeight
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun StorePayAfterScreen(
    navController: NavController,
    name: String,
    phone: String,
    address: String,
    point: String
) {
    BackToStore(navController)

    Column() {
        SingleTitleTopAppBarFormat("결제 완료")

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
                UserInfoFrame(name = name, phone = phone, address = address)
            }

            item {
                SpacerHeight()
            }

            item {
                TextFormat(text = "상품 정보")

                PayCartItemList.forEach{item ->
                    ItemInfoFrame(item = item, navController)
                }
            }

            item {
                SpacerHeight()
            }

            item {
                FinalPayPriceFrame(price = PayCartItemList.sumOf { it.price }, point = point.toInt())
            }

            item {
                SpacerHeight()
            }

            item {
                // 스토어로 돌아가는 버튼
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    TextButtonFormat(text = "스토어로 돌아가기", backgroundColor = Point, borderColor = Color.LightGray, textColor = Color.White, widthSize = 120, shape = RoundedCornerShape(8.dp)) {
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
