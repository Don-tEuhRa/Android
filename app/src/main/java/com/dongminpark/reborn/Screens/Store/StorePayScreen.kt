package com.dongminpark.reborn.Screens.Store

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.CheckBoxButton
import com.dongminpark.reborn.Buttons.ClearTextButton
import com.dongminpark.reborn.Buttons.LongTextButtonFormat
import com.dongminpark.reborn.Buttons.TextButtonFormat
import com.dongminpark.reborn.Frames.*

@Composable
fun StorePayScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            UserInfoFrame(name = "박동민", phone = "010-2245-3683", address = "수원시 장안구 연무동 123-456")
        }

        item {
            // 상품 정보
            TextFormat(text = "상품 정보")

            itemList.forEach{item ->
                ItemInfoFrame(item = item.toString())
            }
        }

        item {
            // 포인트 사용
            TextFormat(text = "포인트 사용")
            // 보유 포인트  -  숫자
            RowSpaceBetweenFrame(first = "보유 포인트", second = "1200")
            // 사용 포인트  -  숫자 입력 -> textfield쓸때 숫자 키패드 출력 및 특수기호 무시 처리 -> 인하대 결과물 코드 참고
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextFormat(text = "사용 포인트", size = 12)
                TextFormat(text = "1200", size = 12) // textField로 변경
            }
        }

        item {
            // 결제 정보
            TextFormat(text = "결제 정보")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // 카드
                TextButtonFormat(text = "카드", onClick = { /*TODO*/ })
                // 카카오페이
                TextButtonFormat(text = "카카오 페이", onClick = { /*TODO*/ })
                // 네이버페이
                TextButtonFormat(text = "네이버 페이", onClick = { /*TODO*/ })
            }
        }

        item {
            FinalPayPriceFrame(12000, 1200)
        }

        item {
            LongTextButtonFormat(
                count = 3,
                price = "119700",
                onClick = { /*TODO*/ }
            )
        }
    }
}

@Preview
@Composable
fun StorePayScreenPreview() {
    StorePayScreen(navController = rememberNavController())
}