package com.dongminpark.reborn.Screens.Store

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Buttons.*
import com.dongminpark.reborn.Frames.*
import com.dongminpark.reborn.ui.theme.Point

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreShoppingCartScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }
    var selectedItems by remember { mutableStateOf(List(itemList.size) { true }) }
    var allSelected by remember {
        mutableStateOf(selectedItems.count { it } == selectedItems.size)
    }

    var checkDelete by remember { mutableStateOf(false) }

    if (checkDelete) {
        AlertDialog(
            shape = RoundedCornerShape(24.dp),
            onDismissRequest = { },
            title = { TextFormat(text = "알림", size = 20, fontWeight = FontWeight.Bold)},
            text = {
                    TextFormat(text = "선택한 상품을 삭제하시겠습니까?", size = 16, fontWeight = FontWeight.Light)
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            // api 호출
                            checkDelete = false
                            navController.popBackStack()
                            navController.navigate("storeShoppingCart")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Point,
                            contentColor = Color.White
                        )
                    ) {
                        Text("삭제")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { checkDelete = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("취소")
                    }
                }
            }
        )
    }





    Column(modifier = Modifier.fillMaxSize()) {
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
                            TextFormat(text = "전체선택", size = 16, fontWeight = FontWeight.ExtraBold)
                        }

                        // 선택 삭제
                        TextFormat(
                            modifier = Modifier.clickable { // 체크된 리스트 삭제 api 호출
                                checkDelete = true
                            },
                            text = "선택삭제",
                            size = 16,
                            fontWeight = FontWeight.ExtraBold
                        )
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
                                selectedItems = selectedItems.toMutableList()
                                    .also { it[item] = !selectedItems[item] }
                                allSelected = selectedItems.count { it } == selectedItems.size
                            })

                            ItemInfoFrame(item = itemList[item].toString(), navController)
                        }
                        // x버튼
                        ClearTextButton {
                            // itemList.remove(item)
                            // api로 삭제 요청
                            navController.popBackStack()
                            navController.navigate("storeShoppingCart")
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