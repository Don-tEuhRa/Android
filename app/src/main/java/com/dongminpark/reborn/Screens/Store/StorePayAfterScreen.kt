package com.dongminpark.reborn.Screens.Store

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Frames.*

@Composable
fun StorePayAfterScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }

    Column() {
        SingleTitleTopAppBarFormat("장바구니")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ){
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    TextFormat(text = "주문해 주셔서 감사합니다.", size = 28)
                    TextFormat(text = "구매금액의 5%는 환경보호를 위해 기부됩니다", size = 16)
                    TextFormat(text = "리본과 함께 나눔에 동참해주셔서 감사합니다.", size = 16)
                }
            }

            item{
                UserInfoFrame(name = "박동민", phone = "010-2245-3683", address = "수원시 장안구 연무동 123-456")
            }



            item {
                TextFormat(text = "상품 정보")

                itemList.forEach{item ->
                    ItemInfoFrame(item = item.toString())
                }
            }

            item {
                FinalPayPriceFrame(12000, 1200)
            }
        }
    }
}

@Preview
@Composable
fun StorePayAfterScreenPreview() {
    StorePayAfterScreen(navController = rememberNavController())
}