package com.dongminpark.reborn.Screens.Store

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.*
import com.dongminpark.reborn.Frames.*
import com.dongminpark.reborn.Utils.Constants.TAG

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreShoppingCartScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }
    var selectedItems by remember { mutableStateOf(List(itemList.size) { true }) }
    var allSelected by remember {
        mutableStateOf(selectedItems.count { it } == selectedItems.size)
    }

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
                            CheckBoxButton(allSelected, onClick = {
                                allSelected = !allSelected
                                
                                selectedItems.forEachIndexed { index, _ ->
                                    selectedItems = selectedItems.toMutableList()
                                        .also { it[index] = allSelected }
                                }

                            })
                            // 전체선택
                            TextFormat(text = "전체선택", size = 16)
                        }

                        // 오른쪽 끝 선택 삭제
                        TextFormat(text = "선택삭제", size = 16)
                    }
                }

                items(itemList.size) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // item 체크버튼
                            CheckBoxButton(selectedItems[item], onClick = {
                                selectedItems = selectedItems.toMutableList().also { it[item] = !selectedItems[item] }
                                allSelected = selectedItems.count { it } == selectedItems.size
                            })

                            ItemInfoFrame(item = itemList[item].toString())
                        }
                        // x버튼
                        ClearTextButton {
                            // itemList.remove(item)
                            // api로 삭제 요청
                        }
                    }
                }
            }

            LongTextButtonFormat(
                count = selectedItems.count { it },
                price = "119700",
                onClick = {
                    // 페이지 이동
                    navController.navigate("storePay")
                }
            )
        }
    }
}

@Preview
@Composable
fun StoreShoppingCartScreenPreview() {
    StoreShoppingCartScreen(navController = rememberNavController())
}