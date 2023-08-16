package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.MypageUser
import com.dongminpark.reborn.Model.User
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.Utils.Constants.TAG
import kotlinx.coroutines.launch

//화면전환(기부,주문내역), 회원정보수정
//사용자이름, 기부금액, 마일리지금액,기부현황진행사항 갯수, 진행현황 임시텍스트로 대체해둠

@Composable
fun myAppBar(userName: String, userPoint: String) {
    var showProfile by remember { mutableStateOf(false) }
    var showSnackBar by remember { mutableStateOf(false) }

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
        }else{
            myProfile(
                name = userInfo.nickname,
                phoneNumber = userInfo.phone,
                address = userInfo.address,
                detialAddress = userInfo.detailAddress,
                zipcode = userInfo.zipCode.toString(),
                onCloseRequest = { showProfile = false }
            )
        }
    }

    if (showSnackBar){

    }
}


@Composable
fun myProfile(
    name: String,
    phoneNumber: String,
    address: String,
    detialAddress: String,
    zipcode: String,
    onCloseRequest: () -> Unit
) {
    var newName by remember { mutableStateOf("") }
    var newPhoneNumber by remember { mutableStateOf("") }
    var newAddress by remember { mutableStateOf("") }
    var newDetailAddress by remember { mutableStateOf("") }
    var newZipcode by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        //modifier = Modifier.fillMaxHeight(0.9f),
        shape = RoundedCornerShape(24.dp),
        onDismissRequest = { onCloseRequest() },
        title = { Text("회원정보수정") },
        text = {
            LazyColumn(modifier = Modifier.padding(vertical = 12.dp)) {
                item {
                    Text(text = "이름")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = name) },
                        value = newName,
                        singleLine = true,
                        onValueChange = { newValue -> newName = newValue },
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                item {
                    Text(text = "연락처")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = phoneNumber) },
                        value = newPhoneNumber,
                        singleLine = true,
                        onValueChange = { newValue -> newPhoneNumber = newValue }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                item {
                    Text(text = "주소")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = address) },
                        value = newAddress,
                        onValueChange = { newValue -> newAddress = newValue }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                item {
                    Text(text = "상세주소")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = detialAddress) },
                        value = newAddress,
                        onValueChange = { newValue -> newDetailAddress = newValue }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                item { // 숫자만 입력가능하게.
                    Text(text = "우편번호")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = zipcode) },
                        value = newAddress,
                        onValueChange = { newValue -> newZipcode = newValue }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                item { // 맞춰서 수정해주ㅏㅓ요
                    Text(text = "현관 비밀번호")
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = zipcode) },
                        value = newAddress,
                        onValueChange = { newValue -> newZipcode = newValue }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        onCloseRequest()
                        // api 호출
                        val nameAPI = if (newName.isEmpty()) name else newName
                        val phoneNumberAPI = if (newPhoneNumber.isEmpty()) phoneNumber else newPhoneNumber
                        val addressAPI = if (newAddress.isEmpty()) address else newAddress

                        /*
                        if (newName.isNotEmpty() && newPhoneNumber.isNotEmpty() && newAddress.isNotEmpty()) {
                            coroutineScope.launch {
                                coroutineScope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        "회원정보가 수정되었습니다.",
                                        "확인",
                                        SnackbarDuration.Short
                                    )

                                    when (result) {
                                        SnackbarResult.Dismissed -> {
                                            Log.d("TAG", "스낵바 닫아짐")
                                        }
                                        SnackbarResult.ActionPerformed -> {
                                            Log.d("TAG", "스낵바 확인버튼을 누름")
                                        }
                                    }
                                }
                            }
                        }
                         */
                    },
                    modifier = Modifier.fillMaxWidth(0.4f)
                ) {
                    Text("등록")
                }
                Button(
                    onClick = { onCloseRequest() },
                    modifier = Modifier.fillMaxWidth(0.7f),
                ) {
                    Text("취소")
                }
            }
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.offset(y = (-20).dp))
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyScreen(navController: NavController) {
    var isLoading by rememberSaveable { mutableStateOf( true) }
    var userName by rememberSaveable { mutableStateOf("") }
    var userPoint by rememberSaveable { mutableStateOf("") }
    var userDonationPoint by rememberSaveable { mutableStateOf("") }
    var userDonationCount by rememberSaveable { mutableStateOf("") }
    BackOnPressed()

    if (isLoading){
        LoadingCircle()
        RetrofitManager.instance.mypage(
            completion = { responseState, info->

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
    }else{
        Surface(color = Color.White) {
            Scaffold(backgroundColor = Color.White,
                content = {
                    Column {
                        myAppBar(userName, userPoint)
                        Spacer(modifier = Modifier.height(8.dp))
                        myView(introduction = MainContents.introMainDetail, userDonationPoint = userDonationPoint, userDonationCount = userDonationCount, navController)
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
    navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(introduction){ aIntroDetail->
            myDonate(R.drawable.baseline_favorite_24, "금액", "${userDonationPoint}원")
            Spacer(modifier = Modifier.height(20.dp))
            myDonate(R.drawable.t_shirt, "횟수", "${userDonationCount}회")
            Spacer(modifier = Modifier.height(20.dp))
            myDonateOrder(navController)
            Spacer(modifier = Modifier.height(80.dp))
            myInquiry()
            Spacer(modifier = Modifier.height(10.dp))
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
            .background(Color(0xff9BE8D8))
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
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 기부현황 버튼
        Button(
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(top = 35.dp, bottom = 35.dp, start = 55.dp, end = 55.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE7E7E7),
                contentColor = Color.Black
            ),
            onClick = {
                //navController.navigate("donation_status")
                navController.navigate("myDonate")
                Log.d("TAG", "기부현황클릭")
            }
        ) {
            Text(text = "기부현황")
        }

        // 주문현황 버튼
        Button(
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(top = 35.dp, bottom = 35.dp, start = 55.dp, end = 55.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFE7E7E7),
                contentColor = Color.Black
            ),
            onClick = {
                //navController.navigate("donation_status")
                navController.navigate("myOrder")
                Log.d("TAG", "주문현황클릭")
            }
        ) {
            Text(text = "주문현황")
        }
    }
}

@Composable
fun myInquiry() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //Spacer(modifier = Modifier.width(16.dp))
            Image(painter = painterResource(id = R.drawable.baseline_email_24),
                contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(text = "Re:Born 문의하기")
            }
        }
    }
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
                Image(painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = serviceContent.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = serviceContent.date)
                    Text(text = serviceContent.time)
                }
            }
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
    Surface(color = Color.White) {
        Scaffold(backgroundColor = Color(0xFFE6E5E5),
            content = {
                Column {
                    myDonatePageAppBar()
                    Spacer(modifier = Modifier.height(16.dp))
                    myDonatePageProG()
                    Spacer(modifier = Modifier.height(32.dp))
                    myDonatePageList()
                }
            }
        )
    }
}

@Composable
fun myDonatePageProG() {
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
                        Text(text = "1")
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
                        Text(text = "2")
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
                        Text(text = "3")
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
                        Text(text = "4")
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
                        Text(text = "5")
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
fun myDonatePageList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //임시텍스트
            Text(
                text = "수거중",
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 이미지
                Image(
                    painter = painterResource(R.drawable.baseline_favorite_24),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .size(72.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        //임시 텍스트
                        Text(
                            text = "기부자 : 홍길동(닉네임)",
                        )
                        Text(
                            text = "수거 날짜 : 2023.07.22",
                        )
                        Text(
                            text = "연락처 : 010 - XXXX - XXXX",
                        )
                        Text(
                            text = "주소 : XXX - XXXX",
                        )
                    }
                }
            }
        }
    }
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
    Surface(color = Color.White) {
        Scaffold(backgroundColor = Color(0xFFE6E5E5),
            content = {
                Column {
                    myOrderPageAppBar()
                    Spacer(modifier = Modifier.height(16.dp))
                    myOrderPageProG()
                    Spacer(modifier = Modifier.height(32.dp))
                    myOrderPageList()
                }
            }
        )
    }
}

@Composable
fun myOrderPageProG() {
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
                        Text(text = "1")
                        Text(text = "전체")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "2")
                        Text(text = "입금/결제")

                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "3")
                        Text(text = "배송중/")
                        Text(text = "픽업대기")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "4")
                        Text(text = "배송완료/")
                        Text(text = "픽업완료")
                    }
                }
                Box() {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "5")
                        Text(text = "구매확정")
                    }
                }
            }
        }
    }
}

@Composable
fun myOrderPageList() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            //임시텍스트
            Text(
                text = "배송완료",
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_favorite_24),
                    contentDescription = "Your Image",
                    modifier = Modifier
                        .size(72.dp)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    Column() {
                        //임시 텍스트
                        Text(
                            text = "기부자 : 홍길동(닉네임)",
                        )
                        Text(
                            text = "상품이름",
                        )
                        Text(
                            text = "가격 : 10000000",
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //환불요청
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { }) {
                    Text(
                        text = "환불요청",
                        color = Color.Black,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
                //배송조회
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { }) {
                    Text(
                        text = "배송조회",
                        color = Color.Black,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
                //상담사 문의
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { }) {
                    Text(
                        text = "상담사 문의",
                        color = Color.Black,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
                //구매확정
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { }) {
                    Text(
                        text = "구매확정",
                        color = Color.Black,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyScreen() {
    val navController = rememberNavController()
    MyScreen(navController = navController)
//    myDonatePage()
//    myOrderPage()
}

