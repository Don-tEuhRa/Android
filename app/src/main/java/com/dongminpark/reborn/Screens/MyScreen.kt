package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.Model.*
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.BackOnPressed
import com.dongminpark.reborn.Utils.IntroductionDetail
import com.dongminpark.reborn.Utils.MainContents
import com.dongminpark.reborn.Utils.customerServiceCenter
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Screens.Store.LikedItemList
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.Utils.Constants.TAG
import com.dongminpark.reborn.Utils.GetAddress.searchAddress
import com.dongminpark.reborn.ui.theme.Point

@Composable
fun myAppBar(navController: NavController, userName: String, userPoint: String) {
    var showProfile by remember { mutableStateOf(false) }
    var editMsg by remember { mutableStateOf(false) }

    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(120.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Text(
                        text = "${userName}님 \n안녕하세요!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "회원정보 수정하기>",
                        fontSize = 12.sp,
                        lineHeight = 20.sp,
                        color = Color(0xFFE7E7E7),
                        modifier = Modifier.clickable {
                            showProfile = true
                        }
                    )
                }
                Spacer(modifier = Modifier.width(48.dp))
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "나의 Re:Born 마일리지",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${userPoint}원",
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }

    if (showProfile) {
        var isLoading by remember {
            mutableStateOf(true)
        }
        var userInfo by remember {
            mutableStateOf(User())
        }

        if (isLoading) {
            LoadingCircle()

            RetrofitManager.instance.userInfo(
                completion = { responseState, user ->

                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            Log.d(TAG, "api 호출 성공")
                            userInfo = user!!
                            isLoading = false
                        }
                        RESPONSE_STATE.FAIL -> {
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "api 호출 에러")
                        }
                    }
                })
        } else {
            myProfile(
                navController = navController,
                userInput = userInfo.name,
                phoneInput = userInfo.phone,
                placeInput = userInfo.address,
                placeDetailInput = userInfo.detailAddress,
                postInput = userInfo.zipCode.toString(),
                houseNumInput = userInfo.gatePassword,
                onCloseRequest = { showProfile = false },
                onEditButtonClick = { editMsg = true }
            )
        }
    }

    if (editMsg) {
        myEditMsg(
            "회원정보가 수정되었습니다.",
            onCloseRequest = { editMsg = false }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun myProfile(
    navController: NavController,
    userInput: String,
    phoneInput: String,
    placeInput: String,
    placeDetailInput: String,
    postInput: String,
    houseNumInput: String,
    onCloseRequest: () -> Unit,
    onEditButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val shouldShowHouseNum = remember { mutableStateOf(false) }
    var isHouseNumEnabled by remember { mutableStateOf(houseNumInput.isEmpty()) }
    var sendApi by remember { mutableStateOf(false) }
    var isSearchAddress by remember { mutableStateOf(false) }

    var userName by remember { mutableStateOf(userInput) }
    var userPhone by remember { mutableStateOf(phoneInput) }
    var userPlace = remember { mutableStateOf(placeInput) }
    var userPlaceDetail by remember { mutableStateOf(placeDetailInput) }
    var userPost = remember { mutableStateOf(postInput) }
    var userHouseNum by remember { mutableStateOf(houseNumInput) }
    var showDialog = remember {
        mutableStateOf(false)
    }
    var showDialogError = remember {
        mutableStateOf(false)
    }

    val houseNumResource: (Boolean) -> Int = {
        if (it) {
            R.drawable.baseline_visibility_24
        } else {
            R.drawable.baseline_visibility_off_24
        }
    }

    AlertDialog(
        shape = RoundedCornerShape(24.dp),
        onDismissRequest = { onCloseRequest() },
        title = { Text("회원정보수정") },
        text = {

            // 수거날짜, 이름, 연락처, 주소, 상세주소, 우편번호, 현관비밀번호
            LazyColumn( // LazyColumn으로 수정 -> 방문수거 장소 => 마이페이지에 있는것 처럶 주소, 상세주소, 우편번호
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                item {
                    Row() {
                        Text(text = "이름")
                        if (userName.isEmpty()) {
                            Text(
                                text = "(이름을 입력해주세요.)",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        value = userName,
                        onValueChange = { newValue -> userName = newValue },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = if (showDialog.value) Color.Transparent else Color(
                                0xff78C1F3
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                }//이름
                item {
                    Row() {
                        Text(text = "연락처")
                        if (userPhone.isEmpty()) {
                            Text(
                                text = "( '-' 빼고 입력해주세요. )",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        value = userPhone,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = { newValue ->
                            if (newValue.all { it.isDigit() } && newValue.length <= 11) {
                                userPhone = newValue
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = if (showDialog.value) Color.Transparent else Color(
                                0xff78C1F3
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }//연락처
                item {
                    Row() {
                        Text(text = "방문수거 장소")
                        if (userPlace.value.isEmpty()) {
                            Text(
                                text = "(장소를 입력해주세요.)",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                        onClick = { isSearchAddress = true }
                    ) {
                        Text(text = userPlace.value, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }//방문수거
                item {
                    Row() {
                        Text(text = "상세 주소")
                        if (userPlaceDetail.isEmpty()) {
                            Text(
                                text = "(상세주소를 입력해주세요.)",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        value = userPlaceDetail,
                        onValueChange = { newValue -> userPlaceDetail = newValue },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = if (showDialog.value) Color.Transparent else Color(
                                0xff78C1F3
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                }//상세주소
                item {
                    Row() {
                        Text(text = "우편번호") // -> readOnly로 수정
                        if (userPost.value.isEmpty()) {
                            Text(
                                text = "(우편번호를 입력해주세요)",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        value = userPost.value,
                        readOnly = true,
                        onValueChange = {},
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = if (showDialog.value) Color.Transparent else Color(
                                0xff78C1F3
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }//우편번호
                item {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "현관비밀번호")
                        if (!isHouseNumEnabled && userHouseNum.isEmpty()) {
                            Text(
                                text = "(비밀번호를 입력해주세요.)",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                        Checkbox(
                            checked = isHouseNumEnabled,
                            onCheckedChange = {
                                isHouseNumEnabled = it
                            },
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                        Text(text = "없음")
                    }
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userHouseNum,
                        enabled = !isHouseNumEnabled,
                        trailingIcon = {
                            IconButton(onClick = {
                                Log.d("TAG", "Housenum:클릭")
                                shouldShowHouseNum.value = !shouldShowHouseNum.value
                            }) {
                                Icon(
                                    painter = painterResource(
                                        id = houseNumResource(
                                            shouldShowHouseNum.value
                                        )
                                    ),
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (shouldShowHouseNum.value) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        onValueChange = { newValue -> userHouseNum = newValue },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = if (showDialog.value) Color.Transparent else Color(
                                0xff78C1F3
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }//현관비밀번호
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    enabled = !(userPlace.value.isEmpty() || userName.isEmpty() || userPlaceDetail.isEmpty() || userPost.value.isEmpty()
                            || userPhone.length != 11 || (!isHouseNumEnabled && userHouseNum.isEmpty())
                            ),
                    onClick = {
                        if (!(userPlace.value.isEmpty() || userName.isEmpty() || userPlaceDetail.isEmpty() || userPost.value.isEmpty()
                                    || userPhone.length != 11 || (!isHouseNumEnabled && userHouseNum.isEmpty())
                                    )
                        ) {
                            RetrofitManager.instance.mypageUserUpdate(
                                name = userName,
                                phoneNumber = userPhone,
                                address = userPlace.value,
                                addressDetail = userPlaceDetail,
                                zipCode = userPost.value.toInt(),
                                doorPassword = if (isHouseNumEnabled) "" else userHouseNum,
                                completion = { responseState ->
                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            onCloseRequest()
                                            onEditButtonClick()
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(
                                                App.instance,
                                                MESSAGE.ERROR,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })

                        }
                    },
                    modifier = Modifier.fillMaxWidth(0.4f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("등록")
                }
                Button(
                    onClick = { onCloseRequest() },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("취소")
                }
            }
        })

    if (isSearchAddress) {
        val SearchAddressInput = remember { mutableStateOf(TextFieldValue()) }
        AlertDialog(
            onDismissRequest = {
                isSearchAddress = false
            },
            title = {
                Text("주소 검색")
            },
            text = {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = SearchAddressInput.value,
                    onValueChange = { newValue -> SearchAddressInput.value = newValue },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    placeholder = { Text(text = "지번 / 도로명 주소를 입력해 주세요") },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            // API 호출
                            searchAddress(SearchAddressInput.value.text, userPost, userPlace)
                            isSearchAddress = false
                        }
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        cursorColor = if (showDialog.value) Color.Transparent else Color(0xff78C1F3)
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        isSearchAddress = false
                        // api 호출  -> 호출 결과 성공하면 searchAddress = false
                        searchAddress(SearchAddressInput.value.text, userPost, userPlace)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    Color.LightGray.copy(alpha = 0.7f),
                    RoundedCornerShape(8.dp)
                )
        )
    }

    if (sendApi) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("잠시만 기다려 주세요")
            },
            text = {
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

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text("감사합니다!")
            },
            text = {
                Text("기부해주셔서 감사합니다.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        navController.navigate("donate")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp,
                    Color.LightGray.copy(alpha = 0.7f),
                    RoundedCornerShape(8.dp)
                )
        )
    }
    if (showDialogError.value) {
        AlertDialog(
            onDismissRequest = {
                showDialogError.value = false
            },
            title = {
                Text("기부하기 실패")
            },
            text = {
                Text("입력되지않은 칸이 있습니다")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialogError.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier.border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun myEditMsg(
    message: String,
    onCloseRequest: () -> Unit
) {
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { onCloseRequest() },
        title = { Text("수정 완료!") },
        text = { Text(message) },
        confirmButton = {
            Button(
                onClick = { onCloseRequest() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xff78C1F3)
                )
            ) {
                Text(text = "확인")
            }
        },
        modifier = Modifier.border(
            2.dp,
            Color.LightGray.copy(alpha = 0.7f),
            RoundedCornerShape(8.dp)
        )
    )
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyScreen(navController: NavController) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var userName by rememberSaveable { mutableStateOf("") }
    var userPoint by rememberSaveable { mutableStateOf("") }
    var userDonationPoint by rememberSaveable { mutableStateOf("") }
    var userDonationCount by rememberSaveable { mutableStateOf("") }

    BackOnPressed()

    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.mypage(
            completion = { responseState, info ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(TAG, "api 호출 성공")
                        userName = info!!.name
                        userPoint = info.point.toString()
                        userDonationPoint = info.donationPoint.toString()
                        userDonationCount = info.donationCount.toString()
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "api 호출 에러")
                    }
                }
            })
    } else {
        Surface(color = Color.White) {
            Scaffold(backgroundColor = Color.White,
                content = {
                    Column {
                        myAppBar(navController, userName, userPoint)
                        Spacer(modifier = Modifier.height(8.dp))
                        myView(
                            introduction = MainContents.introMainDetail,
                            userDonationPoint = userDonationPoint,
                            userDonationCount = userDonationCount,
                            navController
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun myView(
    introduction: List<IntroductionDetail>,
    userDonationPoint: String,
    userDonationCount: String,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(introduction) { aIntroDetail ->
            myDonate(R.drawable.baseline_favorite_24, "금액", "${userDonationPoint}원")
            Spacer(modifier = Modifier.height(20.dp))
            myDonate(R.drawable.t_shirt, "횟수", "${userDonationCount}회")
            Spacer(modifier = Modifier.height(20.dp))
            myDonateOrder(navController)
            Spacer(modifier = Modifier.height(20.dp))
            rebornAppBarDetailBottom(customerServiceCenter = MainContents.customerServiceCenter)
        }
    }
}

@Composable
fun myDonate(icon: Int, text: String, count: String) {
    if (text == "금액") {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "기부 진행도",
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xffD5FFD0))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이미지
            Image(
                painter = painterResource(icon),
                contentDescription = "Your Image",
                modifier = Modifier.size(72.dp) // 이미지 크기 조절
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    // "지금까지 기부하신 금액" 텍스트를 가운데 정렬
                    Text(
                        text = "지금까지 기부하신 $text",
                        style = TextStyle(fontSize = 16.sp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // "100,000원" 추가
                    Text(
                        text = count,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun myDonateOrder(navController: NavController) {
    val heigth = 90
    val width = 110
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "고객 서비스",
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 기부현황 버튼
        Button(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(height = heigth.dp, width = width.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff9AC5F4),
                contentColor = Color.Black
            ),
            onClick = {
                navController.navigate("myDonate")
            }
        ) {
            Text(text = "기부현황")
        }

        // 주문현황 버튼
        Button(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(height = heigth.dp, width = width.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff99DBF5),
                contentColor = Color.Black
            ),
            onClick = {
                navController.navigate("myOrder")
            }
        ) {
            Text(text = "주문현황")
        }

        // Q&A
        Button(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.size(height = heigth.dp, width = width.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xffA7ECEE),
                contentColor = Color.Black
            ),
            onClick = {
                navController.navigate("MyQnA")
            }
        ) {
            Text(text = " Q&A ")
        }
    }
}

@Preview
@Composable
fun previeww() {
    var navController = rememberNavController()
    myDonateOrder(navController = navController)
}

@Composable
fun rebornAppBarDetailBottom(customerServiceCenter: List<customerServiceCenter>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color.White)
    ) {
        customerServiceCenter.forEach { serviceContent ->
            Row(verticalAlignment = Alignment.Top) {
                //Spacer(modifier = Modifier.width(16.dp))
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Row() {
                        Text(
                            text = serviceContent.title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " (010-2245-3683)",
                            color = Color.Gray,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Text(text = serviceContent.date)
                    Text(text = serviceContent.time)
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(52.dp))
            TextFormat(
                text = "COPYRIGHT (c) 2023. Re:Born. All rights reserved.",
                size = 12,
                color = Color.LightGray
            )
            TextFormat(
                text = "Andrid : 박동민, 최수인 / Backend : 이영학, 이한음",
                size = 12,
                color = Color.LightGray
            )
        }
    }
}


//기부현황페이지
@Composable
fun myDonatePageAppBar() {
    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = "기부현황",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun myDonatePage() {
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    var receiptCount by rememberSaveable {
        mutableStateOf(0)
    }
    var pickupCount by rememberSaveable {
        mutableStateOf(0)
    }
    var reformCount by rememberSaveable {
        mutableStateOf(0)
    }
    var arriveCount by rememberSaveable {
        mutableStateOf(0)
    }
    var productCount by rememberSaveable {
        mutableStateOf(0)
    }
    var donationCount by rememberSaveable {
        mutableStateOf(0)
    }
    var donationList by rememberSaveable { mutableStateOf(List<DonationInfo>(0) { DonationInfo() }) }


    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.mypageDonation(
            completion = { responseState, donationCounts, donationInfoList ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        receiptCount = donationCounts!!.receiptCount
                        pickupCount = donationCounts.pickupCount
                        reformCount = donationCounts.reformCount
                        arriveCount = donationCounts.arriveCount
                        productCount = donationCounts.productCount
                        donationCount = donationCounts.donationCount
                        donationList = donationInfoList!!

                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "api 호출 에러")
                    }
                }
            })
    } else {
        Surface(color = Color.White) {
            Scaffold(backgroundColor = Color(0xFFE6E5E5),
                content = {
                    Column {
                        myDonatePageAppBar()
                        Spacer(modifier = Modifier.height(16.dp))
                        myDonatePageProG(
                            receiptCount = receiptCount,
                            pickupCount = pickupCount,
                            reformCount = reformCount,
                            arriveCount = arriveCount,
                            productCount = productCount,
                            donationCount = donationCount
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        myDonatePageList(donationList = donationList)
                    }
                }
            )
        }
    }
}

@Composable
fun myDonatePageProG(
    receiptCount: Int,
    pickupCount: Int,
    reformCount: Int,
    arriveCount: Int,
    productCount: Int,
    donationCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopStart

        ) {
            Text(text = "기부진행도")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$receiptCount")
                        Image(
                            painter = painterResource(R.drawable.ribbon2),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "접수완료")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$pickupCount")
                        Image(
                            painter = painterResource(R.drawable.local_shipping),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "수거중")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$arriveCount")
                        Image(
                            painter = painterResource(R.drawable.business),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "리폼사도착")

                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$reformCount")
                        Image(
                            painter = painterResource(R.drawable.cut),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "리폼중")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$productCount")
                        Image(
                            painter = painterResource(R.drawable.shopping_bag),
                            contentDescription = "판매중",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "판매중")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$donationCount")
                        Image(
                            painter = painterResource(R.drawable.baseline_favorite_24),
                            contentDescription = "기부완료",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "기부완료")
                    }
                }
            }
        }
    }
}

@Composable
fun myDonatePageList(donationList: List<DonationInfo>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.CenterStart,
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(donationList) { donationInfo ->
                DonationInfoFormat(donationInfo)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun DonationInfoFormat(donationInfo: DonationInfo) {
    val img = when (donationInfo.receiptStatus) {
        "접수완료" -> {
            R.drawable.ribbon2
        }
        "수거중" -> {
            R.drawable.local_shipping
        }
        "배달완료" -> {
            R.drawable.business
        }
        "리폼중" -> {
            R.drawable.cut
        }
        "판매중" -> {
            R.drawable.shopping_bag
        }
        "기부완료" -> {
            R.drawable.baseline_favorite_24
        }
        else -> {
            R.drawable.baseline_favorite_24
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Point),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        // 이미지
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(img),
                contentDescription = "Your Image",
                modifier = Modifier
                    .size(72.dp)
            )
            Text(
                text = donationInfo.receiptStatus,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            val size = 12
            val mod = Modifier
                .padding(4.dp)
                .fillMaxWidth(0.9f)
            TextFormat(modifier = mod, text = "기부자 : ${donationInfo.name}", size = size)
            TextFormat(modifier = mod, text = "기부자 연락처 : ${donationInfo.phoneNumber}", size = size)
            TextFormat(modifier = mod, text = "기부자 주소 : ${donationInfo.address}", size = size)
            //${donationInfo.phoneNumber.slice(0..2)} - ${donationInfo.phoneNumber.slice(3..6)} - ${donationInfo.phoneNumber.slice(7..10)}",
        }
    }
}

@Preview
@Composable
fun prepreview() {
    DonationInfoFormat(
        DonationInfo(
            receiptStatus = "접수완료",
            name = "박동민",
            address = "수원시 장안구 연무동 62-6",
            phoneNumber = "01022453683",
            productId = 0,
            price = 0,
        )
    )
}


//주문현황페이지
@Composable
fun myOrderPageAppBar() {
    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
        )
        Text(
            text = "주문현황",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun myOrderPage() {
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    // orderCount의 인자들
    var allOrderCount by rememberSaveable {
        mutableStateOf(0)
    }
    var payCount by rememberSaveable {
        mutableStateOf(0)
    }
    var deliveryCount by rememberSaveable {
        mutableStateOf(0)
    }
    var deliveredCount by rememberSaveable {
        mutableStateOf(0)
    }
    var completeCount by rememberSaveable {
        mutableStateOf(0)
    }


    var orderList by rememberSaveable { mutableStateOf(List<OrderInfo>(0) { OrderInfo() }) }


    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.mypageOrder(
            completion = { responseState, orderCounts, orderInfoList ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        allOrderCount = orderCounts!!.allOrderCount
                        payCount = orderCounts.payCount
                        deliveryCount = orderCounts.deliveryCount
                        deliveredCount = orderCounts.deliveredCount
                        completeCount = orderCounts.completeCount

                        orderList = orderInfoList!!
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    } else {
        Surface(color = Color.White) {
            Scaffold(backgroundColor = Color(0xFFE6E5E5),
                content = {
                    Column {
                        myOrderPageAppBar()
                        Spacer(modifier = Modifier.height(16.dp))
                        myOrderPageProG(
                            allOrderCount = allOrderCount,
                            payCount = payCount,
                            deliveryCount = deliveryCount,
                            deliveredCount = deliveredCount,
                            completeCount = completeCount
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        myOrderPageList(orderList = orderList)
                    }
                }
            )
        }
    }
}

@Composable
fun myOrderPageProG(
    allOrderCount: Int,
    payCount: Int,
    deliveryCount: Int,
    deliveredCount: Int,
    completeCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopStart

        ) {
            Text(text = "입금/결제")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$allOrderCount")
                        Image(
                            painter = painterResource(R.drawable.ribbon2),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "전체")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$payCount")
                        Image(
                            painter = painterResource(R.drawable.baseline_monetization_on_24),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "입금/결제")

                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$deliveryCount")
                        Image(
                            painter = painterResource(R.drawable.local_shipping),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "배송중/")
                        Text(text = "픽업대기")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$deliveredCount")
                        Image(
                            painter = painterResource(R.drawable.shopping_bag),
                            contentDescription = "Your Image",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "배송완료/")
                        Text(text = "픽업완료")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$completeCount")
                        Image(
                            painter = painterResource(R.drawable.baseline_favorite_24),
                            contentDescription = "기부완료",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = "구매확정")
                    }
                }
            }
        }
    }
}

@Composable
fun myOrderPageList(orderList: List<OrderInfo>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.CenterStart,
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(orderList) { orderInfo ->
                OrderInfoFormat(orderInfo)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun OrderInfoFormat(orderInfo: OrderInfo) {
    val img = when (orderInfo.status) {
        "결제성공" -> {
            R.drawable.baseline_monetization_on_24
        }
        "배송중" -> {
            R.drawable.local_shipping
        }
        "배달완료" -> {
            R.drawable.shopping_bag
        }
        "구매확정" -> {
            R.drawable.baseline_favorite_24
        }
        else -> {
            R.drawable.baseline_favorite_24
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Point),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        // 이미지
        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(img),
                        contentDescription = "Your Image",
                        modifier = Modifier
                            .size(72.dp)
                    )
                    Text(
                        text = orderInfo.status,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    val size = 12
                    val mod = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(0.9f)
                    TextFormat(
                        modifier = mod,
                        text = "구매 일자 : ${orderInfo.paymentDate.slice(0..9)}",
                        size = size
                    )
                    //TextFormat(modifier = mod, text = "기부자 연락처 : ${donationInfo.phoneNumber}", size = size) 가격, 상품명
                    TextFormat(
                        modifier = mod,
                        text = "주소 : ${orderInfo.address} ${orderInfo.addressDetail} (${orderInfo.zipCode})",
                        size = size
                    )
                }
            }
        }
    }
}

@Composable
fun MyQnAScreen(navController: NavController) {
    Column {
        QnATopAppBar(navController)
        QnAContent(navController = navController, currentPage = "기부 문의")

    }

}

@Composable
fun QnATopAppBar(navController: NavController, isAsk: Boolean = true) {
    var opened by remember {
        mutableStateOf(false)
    }
    if (opened) {
        QnA(navController = navController, onCloseRequest = { opened = false })
    }

    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(60.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Q&A",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            if(isAsk) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "접수",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            opened = !opened
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QnAContent(navController: NavController, currentPage: String) {
    var DonatItemsBool by rememberSaveable {
        mutableStateOf(MutableList(0) {
            QnAList()
        })
    }
    var AskItemsBool by rememberSaveable {
        mutableStateOf(MutableList(0) {
            QnAList()
        })
    }

    val DONATE = "기부 문의"
    val ITEM = "상품 문의"

    var currentPageTemp by rememberSaveable { mutableStateOf(currentPage) }

    var isLoading2 by rememberSaveable {
        mutableStateOf(true)
    }

    if (isLoading2) {
        LoadingCircle()
        RetrofitManager.instance.postList(
            completion = {  responseState, donateList, orderList ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        DonatItemsBool.clear()
                        DonatItemsBool.addAll(donateList!!)
                        AskItemsBool.clear()
                        AskItemsBool.addAll(orderList!!)
                        isLoading2 = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(
                            App.instance,
                            MESSAGE.ERROR,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QnATypeButton(
                    modifier = Modifier.weight(1f),
                    currentPage = currentPageTemp,
                    checkingPage = DONATE
                ) {
                    currentPageTemp = DONATE
                }
                QnATypeButton(
                    modifier = Modifier.weight(1f),
                    currentPage = currentPageTemp,
                    checkingPage = ITEM
                ) {
                    currentPageTemp = ITEM
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item{
                    when (currentPageTemp) {
                        DONATE -> AskDonateListShow(navController, DonatItemsBool)
                        ITEM -> AskItemListShow(navController, AskItemsBool)
                    }
                }
            }
        }
    }
}

@Composable
fun AskDonateListShow(navController: NavController, list: MutableList<QnAList>) {
    list.forEach {
        ListContent(navController, content = it)
    }
}

@Composable
fun AskItemListShow(navController: NavController, list: MutableList<QnAList>) {
    list.forEach {
        ListContent(navController, content = it)
    }
}



@Composable
fun MyQnADetailScreen(navController: NavController, postId: Int) {
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    var category by rememberSaveable {
        mutableStateOf("")
    }
    var user by rememberSaveable {
        mutableStateOf("")
    }
    var date by rememberSaveable {
        mutableStateOf("")
    }
    var titleInput by rememberSaveable {
        mutableStateOf("")
    }
    var contentInput by rememberSaveable {
        mutableStateOf("")
    }
    var reviewContent by rememberSaveable {
        mutableStateOf("")
    }
    var reviewDate by rememberSaveable {
        mutableStateOf("")
    }
    var isMine by rememberSaveable {
        mutableStateOf(false)
    }

    if (isLoading){
        RetrofitManager.instance.postReadID(
            postId,
            completion = { responseState, post, reContent, reDate->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        category = post!!.category
                        user = post.name
                        date = post.createdAt
                        titleInput = post.title
                        contentInput = post.content
                        isMine = post.isMe
                        reviewContent = reContent!!
                        reviewDate = reDate!!

                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }else{
        Column {
            QnATopAppBar(navController, false)
            QnAList(
                navController,
                category = category,
                postId = postId,
                user = user,
                date = date,
                isMine = isMine,
                titleInput = titleInput,
                contentInput = contentInput,
                reviewContent = reviewContent,
                reviewDate = reviewDate
            )
        }
    }
}

@Composable
fun ListContent(navController: NavController, content: QnAList) {
    Button(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff9AC5F4),
            contentColor = Color.Black
        ),
        onClick = {
            navController.navigate("myQnADetail/${content.postid}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = content.title
            )
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = content.name)
                Text(text = content.createdAt.slice(0..9))
            }
        }
    }
}

@Composable
fun QnATypeButton(
    modifier: Modifier,
    currentPage: String,
    checkingPage: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                onClick()
                //currentPageTemp = "FOLLOWING"
            },
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = checkingPage,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = if (currentPage == checkingPage)
                    Color.Black
                else Color.Gray
            )
        }
        Divider(
            color = if (currentPage == checkingPage)
                Color.Black
            else Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun QnA(
    navController: NavController,
    titleInput: String = "",
    contentInput: String = "",
    category: String = "",
    onCloseRequest: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val roundCornerShape = RoundedCornerShape(8.dp)
    var titleInput by remember { mutableStateOf(titleInput) }
    var contentInput by remember { mutableStateOf(contentInput) }
    var contentEnabled by remember { mutableStateOf(false) }
    val submitPopup = rememberSaveable { mutableStateOf(false) }
    val category2 = listOf("기부문의", "구매문의")
    var selectText by remember { mutableStateOf(category) }
    var mTextFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
    val maxChar = 100
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        shape = roundCornerShape,
        onDismissRequest = { onCloseRequest() },
        title = { Text(text = "문의접수") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    mTextFieldSize = coordinates.size.toSize()
                                }
                                .border(1.dp, Color.LightGray, roundCornerShape),
                            onClick = { expanded = !expanded }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (selectText.isEmpty()) {
                                    Text(text = "문의 유형", color = Color.LightGray)
                                    if (expanded) {
                                        Image(
                                            painter = painterResource(R.drawable.baseline_keyboard_arrow_up_24),
                                            contentDescription = "Your Image",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                                            contentDescription = "Your Image",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                } else {
                                    Text(text = selectText, color = Color.Black)
                                    if (expanded) {
                                        Image(
                                            painter = painterResource(R.drawable.baseline_keyboard_arrow_up_24),
                                            contentDescription = "Your Image",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    } else {
                                        Image(
                                            painter = painterResource(R.drawable.baseline_keyboard_arrow_down_24),
                                            contentDescription = "Your Image",
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }

                            }
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                        ) {
                            category2.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    selectText = label
                                    expanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }

                }//카테고리

                item {
                    OutlinedTextField(
                        value = titleInput,
                        onValueChange = { newValue -> titleInput = newValue },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray,
                            cursorColor = Color(0xff78C1F3)
                        ),
                        placeholder = { Text(text = "제목") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = roundCornerShape
                    )
                }//제목

                item() {
                    Column() {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            OutlinedTextField(
                                value = contentInput,
                                onValueChange = {
                                    if (it.length <= maxChar) {
                                        contentInput = it
                                    }
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Next
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,
                                    focusedIndicatorColor = Color.LightGray,
                                    unfocusedIndicatorColor = Color.LightGray,
                                    cursorColor = Color(0xff78C1F3)
                                ),
                                placeholder = { Text(text = "내용을 작성해주세요.") },
                                maxLines = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                shape = roundCornerShape
                            )
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "${contentInput.length} / 100",
                                color = Color.LightGray
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "비밀글")
                            Checkbox(
                                checked = contentEnabled,
                                onCheckedChange = {
                                    contentEnabled = it
                                }
                            )
                        }
                    }
                }//내용

            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    QnAButton(
                        modifier = Modifier.weight(1f),
                        text = "문의하기",
                        enabled = titleInput.isNotEmpty() && contentInput.isNotEmpty() && selectText.isNotEmpty(),
                        onQnAClicked = {
                            RetrofitManager.instance.postCreate(
                                title = titleInput,
                                content = contentInput,
                                category = selectText,
                                secret = contentEnabled,
                                completion = { responseState ->
                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            submitPopup.value = true
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                })
                        }
                    )
                    QnAButton(
                        modifier = Modifier.weight(1f),
                        text = "취소",
                        enabled = true,
                        onQnAClicked = {
                            onCloseRequest()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    )
    if (submitPopup.value) {
        AlertDialog(
            onDismissRequest = {
                submitPopup.value = false
            },
            title = {},
            text = {
                Text("문의가 정상적으로 접수되었습니다.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onCloseRequest()
                        submitPopup.value = false
                        navController.navigate("MyQnA")
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff78C1F3)
                    )
                ) {
                    Text("확인")
                }
            },
            modifier = Modifier
                .border(2.dp, Color(0xff78C1F3), roundCornerShape)
                .clip(roundCornerShape)
        )
    }
}

@Composable
fun QnAButton(modifier: Modifier, text: String, enabled: Boolean, onQnAClicked: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff78C1F3),
            contentColor = Color.White
        ),
        onClick = {
            onQnAClicked()
        }) {
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun QnAList(
    navController: NavController,
    postId: Int,
    category: String,
    user: String,
    date: String,
    isMine: Boolean,
    titleInput: String,
    contentInput: String,
    reviewContent: String,
    reviewDate: String
) {
    val roundCornerShape = RoundedCornerShape(8.dp)

    var opened by remember { mutableStateOf(false) }
    var deleteBtn by remember { mutableStateOf(false) }
    var deleteMsg by remember { mutableStateOf(false) }

    if (opened) {
        QnA(
            navController = navController,
            category = category,
            titleInput = titleInput,
            contentInput = contentInput,
            onCloseRequest = { opened = false })
    } //수정팝업

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(roundCornerShape)
                    .border(1.dp, Color.LightGray, roundCornerShape)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = category)
            }
        }//카테고리

        item {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Text(text = user)
                    Text(text = date.slice(0..9))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(roundCornerShape)
                        .border(1.dp, Color.LightGray, roundCornerShape)
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = titleInput)
                }
            }
        }//제목

        item {
            Column() {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(roundCornerShape)
                            .border(1.dp, Color.LightGray, roundCornerShape)
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(text = contentInput)
                    }

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "${contentInput.length} / 100",
                        color = Color.LightGray
                    )
                }
                if (isMine) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //수정 텍스트
                        Text(
                            text = "수정",
                            modifier = Modifier.clickable {
                                opened = !opened
                            }
                        )
                        Text(text = "  /  ")
                        //삭제 텍스트
                        Text(
                            text = "삭제",
                            modifier = Modifier.clickable {
                                deleteBtn = !deleteBtn
                            }
                        )
                    }
                }
            }
        }//내용


        item {
            if (reviewContent.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp)
                    )
                }
            }
        }// ㅡㅡ

        item {
            if (reviewContent.isNotEmpty()) {
                Column() {
                    Text("답변")
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .clip(roundCornerShape)
                                .border(1.dp, Color.LightGray, roundCornerShape)
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Text(text = reviewContent)
                        }
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = reviewDate,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }//답변
    }

    if (deleteBtn) {
        AlertDialog(
            onDismissRequest = {
                deleteMsg = true
                deleteBtn = false
            },
            title = {
                Text("문의삭제")
            },
            text = {
                Text("문의를 삭제하시겠습니까?")
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            RetrofitManager.instance.postDelete(
                                postId,
                                completion = { responseState ->
                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            deleteMsg = true
                                            deleteBtn = false
                                            navController.navigate("MyQnA")
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(
                                                App.instance,
                                                MESSAGE.ERROR,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })
                        },
                        modifier = Modifier.fillMaxWidth(0.4f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xff78C1F3)
                        )
                    ) {
                        Text("삭제")
                    }
                    Button(
                        onClick = {
                            deleteBtn = false
                        },
                        modifier = Modifier.fillMaxWidth(0.7f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray
                        )
                    ) {
                        Text("취소")
                    }
                }
            },
            modifier = Modifier.border(2.dp, Color.LightGray, RoundedCornerShape(8.dp)).clip(roundCornerShape)
        )
    } //삭제하시겠습니까?
    if (deleteMsg) { //삭제완료 팝업
        deleteMsg(
            "삭제가 완료되었습니다.",
            onCloseRequest = { deleteMsg = false }
        )
    }
}

@Composable
fun deleteMsg(
    message: String,
    onCloseRequest: () -> Unit
) {
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { onCloseRequest() },
        title = { Text("삭제완료") },
        text = { Text(message) },
        confirmButton = {
            Button(
                onClick = { onCloseRequest() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xff78C1F3)
                )
            ) {
                Text(text = "확인")
            }
        },
        modifier = Modifier.border(
            2.dp,
            Color.LightGray.copy(alpha = 0.7f),
            RoundedCornerShape(8.dp)
        ).clip(RoundedCornerShape(8.dp))
    )
}
