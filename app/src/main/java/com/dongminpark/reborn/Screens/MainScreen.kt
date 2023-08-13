package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.ui.theme.ReBornTheme

//main에서 detail 화면전환
@Composable
fun rebornAppBar(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color.White) {
        Text(text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xff78C1F3)
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    introductions: List<Introduction>,
    navController: NavController
) {
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
                ProgressBarPager(mutableListOf(ProgressStep(state = "검수중"),ProgressStep(state = "리폼중"),ProgressStep(state = "판매중")))
                Spacer(modifier = Modifier.height(24.dp))
                introductions.forEach { introduction ->
                    introductionView(aIntro = introduction, navController = navController as NavHostController)
                }
            }
        }
    }
}

@Composable
fun introductionView(aIntro: Introduction, navController: NavHostController){
    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 15.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
    )

    Surface(
        color = aIntro.color,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = aIntro.title)
                Text(text = aIntro.subtitle)
                if (expanded) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = aIntro.content)
                }
            }
            Button(
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(if (expanded) "close" else "click")
            }
        }
    }
}


//MainScreen_Detail
@Composable
fun rebornAppBarDetail(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(100.dp)
    ) {
        val introduction=MainContents.introMain[0]
        Column(modifier = Modifier
            .padding(8.dp)) {
            Text(text = stringResource(id = R.string.app_name),
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Text(text =introduction.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
            Text(
                text = introduction.subtitle,
                lineHeight = 20.sp
            )
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun mainScreenDetail(navController: NavHostController) {
    Surface(color = Color.White) {
        Scaffold(backgroundColor = Color.White,
            content = {
                Column {
                    rebornAppBarDetail()
                    introductionViewDetail(introduction = MainContents.introMainDetail)
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
        )
    }
}

@Composable
fun introductionViewDetail(introduction: List<IntroductionDetail>){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        application()
        Spacer(modifier = Modifier.height(40.dp))
        checklist()
        Spacer(modifier = Modifier.height(60.dp))

    }
}

@Composable
fun application() {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .background(Color(0xff78C1F3))
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "신청 방법",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .background(Color(0xFFD3E3F3))
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                Column() {
                    // 1번 원
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xff78C1F3), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = buildAnnotatedString {
                            append("회원가입 후 ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("로그인")
                            }
                        })
                    }

                    // 2번 원
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xff78C1F3), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "2",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = buildAnnotatedString {
                            Text(text = buildAnnotatedString {
                                append("하단의 ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("선물상자")
                                }
                                append(" 버튼을 click!")
                            })
                        })
                    }

                    // 3번 원
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xff78C1F3), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "3",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("주소, 수거 날짜, 휴대번호, ")
                                }
                            })
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("현관 정보 입력")
                                }
                                append("후 신청")
                            })
                        }
                    }

                    // 4번 원
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(Color(0xff78C1F3), shape = CircleShape)
                                .clip(RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "4",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = buildAnnotatedString {
                                append("마이페이지를 통해 ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("[기부 현황]")
                                }
                                append(" 및 ")
                            })
                            Text(text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("[주문 현황]")
                                }
                                append(" 을 확인할 수 있어요")
                            })
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun checklist(){
    Box(
        modifier = Modifier.clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFE6E6E6))
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "체크리스트",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // 1번 원
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xff78C1F3), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "v",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = buildAnnotatedString {
                        append("신발과 특수재질의 옷은 안돼요.")
                    })
                }

                // 2번 원
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xff78C1F3), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "v",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = buildAnnotatedString {
                        append("옷은 최소 1키로부터 수거 가능해요.")
                    })

                }

                // 3번 원
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xff78C1F3), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "v",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))

                    Column() {
                        Text(text = buildAnnotatedString {
                            append("리폼을 원하는 옷은 박스를 밀봉해서")
                        })
                        Text(text = buildAnnotatedString {
                            append("준비해주세요!")
                        })
                    }

                }

                // 4번 원
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(0xff78C1F3), shape = CircleShape)
                            .clip(RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "v",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = buildAnnotatedString {
                        append("수거가 완료된 의류는 수 톤 단위의 의류들과 합쳐져 반환이 어려워요.")
                    })
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ReBornTheme {
        val navController = rememberNavController()
        MainScreen(modifier = Modifier, introductions = MainContents.introMain,navController)

    }
}

