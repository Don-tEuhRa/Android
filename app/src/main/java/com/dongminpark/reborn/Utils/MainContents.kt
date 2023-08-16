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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dongminpark.reborn.R


data class Introduction(
    val title:String,
    val subtitle:String,
    val content: @Composable (Modifier) -> Unit,
    val color: Color,
    val fontColor: Color,
    val icon : Int,
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
            color = Color(0xff78C1F3),Color.Black,
            icon = R.drawable.ribbon2
            ),

        Introduction("신청방법 : ", "이용가이드"
            ,content={ application() },
            color = Color(0xff374259),Color.White,
            icon = R.drawable.baseline_fact_check_24
            ),

        Introduction("기부", "어떤식으로 진행되나요?"
            ,content={ donateExplain() },
            color = Color(0xff45CFDD),Color.Black,
            icon = R.drawable.baseline_donate_24
            ),

        Introduction("수익금", "어떻게 사용되나요?"
            ,content={ fundsExplain() },
            color = Color(0xff9BABB8),Color.Black,
            icon = R.drawable.baseline_monetization_on_24
            )

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
        Text(text = "\"Re:Born\"은 버려진 의류를 재탄생시켜\n"+
                "환경을 보호하고자 하는 프로젝트입니다.\n")
        Text(
            text = "리본 등장 배경",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ))
        Text(text = "삼성 패션 연구소의 보고서에 따르면\n" +
                "지난해 10월 기준 패션 소매판매액은 9% 높아진 62조 원 대가 전망된다는 사실을 아시나요?\n")
        Text(text = "현재 대한민국의 헌 옷 수출량은 미국, 중국, 영국, 독일 다음으로 전 세계 5위에 달합니다.\n\n"+
                "하지만, 수출된 의류 또한 소비되지 못해\n의류 쓰레기가 모여 산을 만들 정도입니다.\n")
        Text(text = "이러한 상황에서 \"Re:Born\"은 버려지는 옷을\n기부받고, 재탄생 시켜 환경을 보호하고자합니다")
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
        Text(
            text = "Q. 옷 기부는 어떻게 진행되나요?",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ))
        Text(text = "기부신청을 통해 수거된 옷은 검수과정을 거쳐\n재가공이 가능한지 따져 리폼됩니다. " +
                "검수된 옷은 재탄생되어 Re:Born마켓을 통해 판매됩니다.\n")
        Text(text = "또한 옷을 기부하신 기부자들에게 판매 완료된\n옷에 대한 포인트를 제공하여 기부에 " +
                "대한 보상과 감사의 마음을 전합니다.\n")
        Text(
            text = "Q. 옷을 사면 기부가 되는건가요?",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ))
        Text(text = "Re:Born마켓에서 판매되는 제품을 구매하실때\n수익금이 기부됩니다.")
    }
}

@Composable
fun fundsExplain(){
    Text("Re:Born은 운영비를 제외한 모든 수익금은\n "+"\"대한환경운동연합KFEM\"와 일부는 취약계층에게 기부됩니다.")
}