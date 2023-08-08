package com.dongminpark.reborn.Utils

import androidx.compose.ui.graphics.Color


data class Introduction(
    val title:String,
    val subtitle:String,
    val content:String,
    val color: Color,
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
        Introduction("Reborn! 이용가이드 :", "어떻게 진행되나요?"
            ,"수거신청하고, 문앞에 두면 끝", color = Color(0xff78C1F3)),
        Introduction("기부 안내:", "어디에 기부되나요?"
            ,"안녕하십니까",color = Color(0xff374259)),
        Introduction("이용방법 3", "어떻게 이용할 수 있나요?"
            ,"잘지내시나요",color = Color(0xff45CFDD)),
        Introduction("이용방법 4", "어떻게 이용할 수 있나요?"
            ,"반갑습니다",color = Color(0xff9BABB8))
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
            "AM 8:00 ~ PM 7:00",
        )
    )
}