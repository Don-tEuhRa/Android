package com.dongminpark.reborn.Screens.Store

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.*
import com.dongminpark.reborn.Frames.*

@Composable
fun StoreShoppingCartScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }

    Column() {
        SingleTitleTopAppBarFormat("장바구니")

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 0.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 체크버튼
                            CheckBoxButton(onClick = { /*TODO*/ })
                            // 전체선택
                            TextFormat(text = "전체선택", size = 16)
                        }

                        // 오른쪽 끝 선택 삭제
                        TextFormat(text = "선택삭제", size = 16)
                    }
                }
                items(itemList) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // item 체크버튼
                            CheckBoxButton(onClick = { /*TODO*/ })

                            ItemInfoFrame(item = item.toString())

                            /*
                            // item 이미지
                            ImageFormat(url = "testurl", size = 120)

                            // item 이름, 가격
                            Column(modifier = Modifier.padding(8.dp)) {
                                TextFormat(text = "반팔티셔츠")
                                TextFormat(text = "10000원")
                            }

                             */
                        }
                        // x버튼
                        ClearTextButton {
                            //itemList.remove(item)
                        }
                    }
                }
            }

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
fun StoreShoppingCartScreenPreview() {
    StoreShoppingCartScreen(navController = rememberNavController())
}