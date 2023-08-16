package com.dongminpark.reborn.Screens.Store

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun StorePayScreen(navController: NavController) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isPaying by rememberSaveable { mutableStateOf(false) }
    var userName by rememberSaveable { mutableStateOf("") }
    var userPhone by rememberSaveable { mutableStateOf("") }
    var userAddress by rememberSaveable { mutableStateOf("") }
    var userDetailAddress by rememberSaveable { mutableStateOf("") }
    var userZipcode by rememberSaveable { mutableStateOf("") }
    var userPoint by rememberSaveable { mutableStateOf("") }

    if (isPaying){
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("잠시만 기다려 주세요")
            },
            text = {
                Text("결제를 진행중입니다.")
            },
            confirmButton = {},
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    Color.LightGray.copy(alpha = 0.7f),
                    RoundedCornerShape(8.dp)
                )
        )
    }


    if (isLoading){
        LoadingCircle()
        RetrofitManager.instance.userInfo(
            completion = { responseState, user ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(Constants.TAG, "api 호출 성공")
                        userName = user!!.name
                        userPhone = user.phone
                        userAddress = user.address
                        userDetailAddress = user.detailAddress
                        userZipcode = user.zipCode.toString()
                        userPoint = user.point.toString()

                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "api 호출 에러")
                    }
                }
            })
    }else{
        val totalAmount by rememberSaveable { mutableStateOf("${PayCartItemList.sumOf { it.price }}") }
        var selectedPayment by rememberSaveable { mutableStateOf("신용카드") }
        var usePoint by remember { mutableStateOf("") }
        val deliveryPrice = 4000

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
                        name = userName,
                        phone = userPhone,
                        address = "$userAddress $userDetailAddress ($userZipcode)"
                    )
                }

                item {
                    SpacerHeight()
                }

                item {
                    // 상품 정보
                    TextFormat(text = "상품 정보")

                    PayCartItemList.forEach { item ->
                        ItemInfoFrame(item = item, navController)
                    }
                }

                item {
                    SpacerHeight()
                }

                item {
                    // 포인트 사용
                    TextFormat(modifier = Modifier.padding(vertical = 8.dp), text = "마일리지 사용")

                    // 보유 포인트  -  숫자
                    RowSpaceBetweenFrame(first = "보유 마일리지", second = "${userPoint}원")
                    // 사용 포인트  -  숫자 입력 -> textfield쓸때 숫자 키패드 출력 및 특수기호 무시 처리 -> 인하대 결과물 코드 참고
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextFormat(text = "사용 마일리지", size = 12)

                        Row() {
                            BasicTextField(
                                modifier = Modifier
                                    .size(width = (userPoint.length * 8).dp, height = 20.dp)
                                    .background(Color.LightGray),
                                value = usePoint,
                                onValueChange = { newText ->
                                    if (newText.isNotEmpty() &&newText.all { it.isDigit() }) {
                                        if (newText.toInt() > userPoint.toInt()) {
                                            Toast.makeText(
                                                App.instance,
                                                "보유 마일리지보다 많이 사용할수 없어요",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            usePoint = userPoint
                                        } else {
                                            usePoint = newText
                                        }
                                    }else {
                                        usePoint = ""
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
                            TextFormat(text = "원", size = 12)
                        }
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
                            backgroundColor = if (selectedPayment == "신용카드") Point else Color.White,
                            borderColor = if (selectedPayment == "신용카드") Color.White else Color.LightGray,
                            textColor = if (selectedPayment == "신용카드") Color.White else Color.Black,
                            onClick = {
                                selectedPayment = "신용카드"
                            }
                        )
                        // 계좌이체
                        TextButtonFormat(
                            text = "계좌이체",
                            backgroundColor = if (selectedPayment == "계좌이체") Point else Color.White,
                            borderColor = if (selectedPayment == "계좌이체") Color.White else Color.LightGray,
                            textColor = if (selectedPayment == "계좌이체") Color.White else Color.Black,
                            onClick = {
                                selectedPayment = "계좌이체"
                            }
                        )
                    }
                }

                item {
                    SpacerHeight()
                }

                item {
                    FinalPayPriceFrame(price = totalAmount.toInt(), point = if (usePoint.isNotEmpty()) usePoint.toInt() else 0)
                }

                item {
                    SpacerHeight()
                }

                item {
                    LongTextButtonFormat(
                        count = 3,
                        price = "${totalAmount.toInt() + deliveryPrice - if (usePoint.isNotEmpty()) usePoint.toInt() else 0}",
                        onClick = {
                            isPaying = true
                            navController.navigate("storePayAfter/${userName}/${userPhone}/$userAddress $userDetailAddress ($userZipcode)/${if (usePoint.isNotEmpty()) usePoint.toInt() else 0}")

                            RetrofitManager.instance.orderCreate(
                                usePoint = if (usePoint.isNotEmpty()) usePoint.toInt() else 0,
                                payMethod = selectedPayment,
                                productId = PayCartItemList.map { it.productId },
                                completion = { responseState->

                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            navController.navigate("storePayAfter/${userName}/${userPhone}/$userAddress $userDetailAddress ($userZipcode)/${if (usePoint.isNotEmpty()) usePoint.toInt() else 0}")
                                            isPaying = false
                                            Log.d(Constants.TAG, "api 호출 성공")
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                            Log.d(Constants.TAG, "api 호출 에러")
                                        }
                                    }
                                })
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun StorePayScreenPreview() {
    StorePayScreen(navController = rememberNavController())
}