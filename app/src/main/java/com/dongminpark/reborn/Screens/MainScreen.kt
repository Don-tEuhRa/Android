package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.ProgressBar
import com.dongminpark.reborn.Model.Review
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.ui.theme.ReBornTheme

@Composable
fun rebornAppBar() {
    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xff78C1F3)
        )
    }
}

var progressBar = mutableListOf<ProgressBar>()
var reviewObj = Review()

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    introductions: List<Introduction>,
    navController: NavController
) {
    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    BackOnPressed()

    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.reviewRandom(
            completion = { responseState, review ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        reviewObj = review!!
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        RetrofitManager.instance.progressList(
            completion = { responseState, progress ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        progressBar = progress!!
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    } else {
        Surface(color = Color.White) {
            Scaffold(backgroundColor = Color.White,
                topBar = { rebornAppBar() }
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    ProgressBarPager(progressBar)
                    Spacer(modifier = Modifier.height(8.dp))
                    reviewBar(reviewObj, navController)
                    Spacer(modifier = Modifier.height(8.dp))
                    introductions.forEach { introduction ->
                        introductionView(
                            aIntro = introduction,
                            navController = navController as NavHostController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun introductionView(aIntro: Introduction, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    Button(
        onClick = { expanded = !expanded },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = aIntro.color,
            contentColor = aIntro.fontColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 30.dp, top = 15.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(text = aIntro.title)
                    Text(text = aIntro.subtitle)
                }
                Icon(
                    painter = painterResource(id = aIntro.icon),
                    contentDescription = "Icon",
                    Modifier.size(36.dp)
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(16.dp))
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    aIntro.content(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun reviewBar(
    review: Review,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Re:Born 사용자들의 생생한 리뷰모음")
            Text(
                text = "더보기>",
                modifier = Modifier.clickable {
                    navController.navigate("mainReview")
                },
                color = Color.Gray
            )
        }
        SatisfactionBar(
            name = "박동민",//review.userName,
            star = 5,//review.star,
            date = "2023-08-18",//review.createdAt,
            content = "수거 과정이 빠르고 문의 담당자가 친절해서 좋았어요!"//review.content
        )
    }
}

@Composable
fun SatisfactionBar(
    modifier: Modifier = Modifier,
    name: String,
    star: Int,
    date:String,
    content:String
){
    val roundCornerShape = RoundedCornerShape(24.dp)
    //리뷰박스
    Button(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundCornerShape),
        shape = roundCornerShape,
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff78C1F3),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(text = name)
                    Text(text = date.slice(0..9))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    repeat(star){
                        Image(painter = painterResource(id = R.drawable.baseline_star_24),
                            contentDescription = "star")
                    }
                    repeat(5-star){
                        Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
                            contentDescription = "star_border")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ReBornTheme {
        val navController = rememberNavController()
        MainScreen(modifier = Modifier, introductions = MainContents.introMain, navController)
    }
}

@Composable
fun reviewBar(
    contentInput:String = ""
) {
    var survey by remember { mutableStateOf(true) }
    var reviewThank by remember { mutableStateOf(false) }

    var contentInput by remember { mutableStateOf(contentInput) } //리뷰작성위한
    val maxChar = 100 //리뷰작성을 위한
    var starTouch by remember { mutableStateOf(0) }

    //설문조사 팝업
    if (survey) {//유효성검사 필요
        AlertDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            onDismissRequest = { survey = false },
            title = { Text("Review") },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    //설문조사
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Re:Born 서비스를 통해 기부에 대한 인식이 개선되었다.")
                            Row() {
                                RadioBtn()
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "매우불만족",
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "매우 만족",
                                    fontSize = 10.sp
                                )
                            }

                        }

                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Re:Born을 통한 나의 기부 과정이 투명하게 보여 졌다고 느낀다.")
                            Row() {
                                RadioBtn()
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "매우불만족",
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "매우 만족",
                                    fontSize = 10.sp
                                )
                            }

                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Re:Born 쇼핑몰에 대해 만족한다.")
                            Row() {
                                RadioBtn()
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "매우불만족",
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "매우 만족",
                                    fontSize = 10.sp
                                )
                            }

                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(text = "Re:Born 어플을 다시 이용할 의향이 있다.")
                            Row() {
                                RadioBtn()
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "매우불만족",
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = "매우 만족",
                                    fontSize = 10.sp
                                )
                            }

                        }
                    }
                    //리뷰내용
                    item {
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
                                        .height(50.dp),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "${contentInput.length} / 100",
                                    color = Color.LightGray
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    //별
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text("리뷰하기")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                //함수
                                Button(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { starTouch =1 },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )
                                ) {
                                    Text(text = "1")
                                }
                                Button(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { starTouch =2 },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )) {
                                    Text(text = "2")
                                }
                                Button(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { starTouch =3 },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )) {
                                    Text(text = "3")
                                }
                                Button(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { starTouch =4 },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )) {
                                    Text(text = "4")
                                }
                                Button(
                                    modifier = Modifier.size(40.dp),
                                    onClick = { starTouch =5 },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.White
                                    )) {
                                    Text(text = "5")
                                }

                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                repeat(starTouch){
                                    Image(painter = painterResource(id = R.drawable.baseline_star_24),
                                        contentDescription = "star")
                                }
                                repeat(5-starTouch){
                                    Image(painter = painterResource(id = R.drawable.baseline_star_border_24),
                                        contentDescription = "star_border")
                                }
                            }
                        }

                    }

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        survey = false
                        reviewThank=true
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray
                    )
                ) {
                    Text("확인")
                }
            }
        )
    }
    //설문조사 완료 감사 팝업
    if (reviewThank) {
        AlertDialog(
            onDismissRequest = {
                reviewThank = false
            },
            title = {
                Text("리뷰")
            },
            text = {
                Text("감사합니다.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        reviewThank = false
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
}


@Composable
fun RadioBtn(){
    val radioButton = listOf(0,1,2,3,4)
    val selectionBnt= remember {
        mutableStateOf(radioButton.indexOf(5))
    }
    radioButton.forEach {
        val isSelected = it ==selectionBnt.value
        val colors=RadioButtonDefaults.colors(
            selectedColor = Color(0xff78C1F3),
            unselectedColor = Color.LightGray,
            disabledColor = Color.LightGray
        )
        RadioButton(
            colors = colors,
            selected = isSelected,
            onClick = { selectionBnt.value =  it })
    }
}