package com.dongminpark.reborn.Utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Introduction(
    val title:String,
    val subtitle:String,
    val content: @Composable (Modifier) -> Unit,
    val color: Color,
    val fontColor: Color,
   // val icon : Int,
    var isSubtitleDisplayed: Boolean = true
)

data class IntroductionDetail(
    val title:String,
    val content1:String,
    val content2:String,
    val content3:String,
    val content4:String,
)

data class IntroductionDetail2(
    val title:String,
    val content1:String,
    val content2:String,

    )

data class customerServiceCenter(
    val title:String,
    val date:String,
    val time:String
)

object MainContents {
    val introMain= listOf(
        Introduction("Re:Born", "어떤 앱인가요?"
            ,content={ appExplain() },
            color = Color(0xff78C1F3),Color.Black),

        Introduction("신청방법 : ", "이용가이드"
            ,content={ application() },
            color = Color(0xff374259),Color.White),

        Introduction("기부", "어떤식으로 진행되나요?"
            ,content={ donateExplain() },
            color = Color(0xff45CFDD),Color.Black),

        Introduction("수익금", "어떻게 사용되나요?"
            ,content={ fundsExplain() },
            color = Color(0xff9BABB8),Color.Black)
    )

    val introMainDetail= listOf(
        IntroductionDetail("수거신청",
            "회원가입 후 로그인",
            "하단의 선물상자 버튼을 click!",
            "주소, 수거 날짜, 휴대번호, 현관 정보 입력 후 신청",
            "마이페이지를 통해 [기부 현황] 및 [주문 현황]을 확인할 수 있어요",
        )
    )

    val introMainDetail2=listOf(
        IntroductionDetail2("체크리스트",
            "앱 푸시 알람을 통해 수거신청 진행사항 알림을 받을 수 있어요",
            "수거가 완료된 의류는 수 톤 단위의 의류들과 합쳐져 반환이 어려워요",
        )
    )

    val customerServiceCenter=listOf(
        customerServiceCenter("고객센터",
            "월요일 ~ 금요일 (주말 및 공휴일 휴무)",
            "AM 10:00 ~ PM 5:00",
        )
    )
}

@Composable
fun appExplain(){
    Text(text = "리본의 대한 설명")
}

@Composable
fun application() {
    Spacer(modifier = Modifier.height(8.dp))
    Column() {
        // 1번 원
        Row() {
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
        Spacer(modifier = Modifier.height(16.dp))

        // 2번 원
        Row() {
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
        Spacer(modifier = Modifier.height(16.dp))

        // 3번 원
        Row() {
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
        Spacer(modifier = Modifier.height(8.dp))

        // 4번 원
        Row() {
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

@Composable
fun donateExplain(){
    Column() {
        Text(text = "기부가 어떤식으로 진행되고 어디에 기부되는지")
        Text(text = "기부금은 여러분들이 옷을 살 때")
    }
}

@Composable
fun fundsExplain(){
    Text("수익금이 어떤식으로 사용되는지, 어디에 기부되는지")
}