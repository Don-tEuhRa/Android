package com.dongminpark.reborn.Screens.Store

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Buttons.LongTextButtonFormat
import com.dongminpark.reborn.Buttons.TextButtonFormat
import com.dongminpark.reborn.Frames.*
import com.dongminpark.reborn.Utils.SpacerHeight

@Composable
fun StorePayScreen(navController: NavController) {
    val itemList by rememberSaveable { mutableStateOf(mutableListOf(1, 2, 3)) }
    var selectedPayment by rememberSaveable { mutableStateOf("Card") }

    val totalAmount by remember { mutableStateOf("12000") }
    val deliveryPrice = 4000
    var holdingPoint by remember { mutableStateOf("1200") }
    var point by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column() {
        SingleTitleTopAppBarFormat("주문서 작성")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 0.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            item {
                SpacerHeight()
            }

            item {
                UserInfoFrame(
                    name = "박동민",
                    phone = "010-2245-3683",
                    address = "수원시 장안구 연무동 123-456"
                )
            }

            item {
                SpacerHeight()
            }

            item {
                // 상품 정보
                TextFormat(text = "상품 정보")

                itemList.forEach { item ->
                    ItemInfoFrame(item = item.toString())
                }
            }

            item {
                SpacerHeight()
            }

            item {
                // 포인트 사용
                TextFormat(modifier = Modifier.padding(vertical = 8.dp), text = "포인트 사용")

                // 보유 포인트  -  숫자
                RowSpaceBetweenFrame(first = "보유 포인트", second = "1200")
                // 사용 포인트  -  숫자 입력 -> textfield쓸때 숫자 키패드 출력 및 특수기호 무시 처리 -> 인하대 결과물 코드 참고
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextFormat(text = "사용 포인트", size = 12)

                    BasicTextField(
                        modifier = Modifier
                            .size(width = (holdingPoint.length*8).dp, height = 20.dp)
                            .background(Color.LightGray),
                        value = point,
                        onValueChange = { newText ->
                            if (newText.isNotEmpty() &&newText.all { it.isDigit() }) {
                                if (newText.toInt() > holdingPoint.toInt()) {
                                    Toast.makeText(
                                        App.instance,
                                        "보유 포인트보다 많이 사용할 수 없습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    point = holdingPoint
                                } else {
                                    point = newText
                                }
                            }else {
                                point = ""
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 12.sp,
                            color = Color.Black
                        ),
                        singleLine = true
                    )
                }
            }

            item {
                SpacerHeight()
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
                    TextButtonFormat(
                        text = "카드",
                        backgroundColor = if (selectedPayment == "Card") Color.Red else Color.White,
                        borderColor = if (selectedPayment == "Card") Color.White else Color.Black,
                        textColor = if (selectedPayment == "Card") Color.White else Color.Black,
                        onClick = {
                            selectedPayment = "Card"
                        }
                    )
                    // 카카오페이
                    TextButtonFormat(
                        text = "카카오 페이",
                        backgroundColor = if (selectedPayment == "KakaoPay") Color.Red else Color.White,
                        borderColor = if (selectedPayment == "KakaoPay") Color.White else Color.Black,
                        textColor = if (selectedPayment == "KakaoPay") Color.White else Color.Black,
                        onClick = {
                            selectedPayment = "KakaoPay"
                        }
                    )
                    // 네이버페이
                    TextButtonFormat(
                        text = "네이버 페이",
                        backgroundColor = if (selectedPayment == "NaverPay") Color.Red else Color.White,
                        borderColor = if (selectedPayment == "NaverPay") Color.White else Color.Black,
                        textColor = if (selectedPayment == "NaverPay") Color.White else Color.Black,
                        onClick = {
                            selectedPayment = "NaverPay"
                        }
                    )
                }
            }

            item {
                SpacerHeight()
            }

            item {
                FinalPayPriceFrame(price = totalAmount.toInt(), point = if (point.isNotEmpty()) point.toInt() else 0)
            }

            item {
                SpacerHeight()
            }

            item {
                LongTextButtonFormat(
                    count = 3,
                    price = "${totalAmount.toInt() + deliveryPrice - if (point.isNotEmpty()) point.toInt() else 0}",
                    onClick = {
                        navController.navigate("storePayAfter")
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun StorePayScreenPreview() {
    StorePayScreen(navController = rememberNavController())
}