package com.dongminpark.reborn.Utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.Model.ProgressBar
import com.dongminpark.reborn.R
import com.dongminpark.reborn.ui.theme.MyIconPack
import com.dongminpark.reborn.ui.theme.Point
import com.dongminpark.reborn.ui.theme.ProgressBGColor
import com.dongminpark.reborn.ui.theme.myiconpack.Ribbon
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ProgressBar(name: String, count: Int, progress: String) {
    var firtStep: ProgressStep = ProgressStep("수거중")
    var secondStep: ProgressStep = ProgressStep("검수중")
    var thirdStep: ProgressStep = ProgressStep("리폼중")
    var fourthStep: ProgressStep = ProgressStep("판매중")
    var state: String = "에러"
    var progressPercent = 0

    @Composable
    fun circleIcon(){
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.circle),
            contentDescription = "circle",
            tint = Color.Gray
        )
    }

    val MainColor = Point

    when (progress) {
        "접수완료" -> {
            state = "리본을 기다리는 중"
        }
        "수거중" -> {
            state = "여행하는 중"
            firtStep = ProgressStep("수거 중", Point, R.drawable.truck_filled, MainColor)
            progressPercent = 100
        }
        "검수중" -> {
            state = "꿈을 찾는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 중", Point, R.drawable.search, MainColor)
            progressPercent = 190
        }
        "리폼중" -> {
            state = "꽃단장 하는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 완료", Point)
            thirdStep = ProgressStep("리폼 중", Point, R.drawable.needle, MainColor)
            progressPercent = 250
        }
        "판매중" -> {
            state = "새로운 주인을 기다리는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 완료", Point)
            thirdStep = ProgressStep("리폼 완료", Point)
            fourthStep = ProgressStep("판매 중", Point, R.drawable.gift_solid, MainColor)
            progressPercent = 320
        }
        "기부완료" -> {
            state = "리본과 함께 따뜻한 마음을 전달했어요"
            progressPercent = 380
        }
    }

    Box(
        modifier = Modifier
            .size(height = 200.dp, width = 380.dp)
            .background(ProgressBGColor, shape = RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextFormat(text = "${name}님이 ${count}번째 기부하신 물건이\n${state} ~!!", size = 16)
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = MyIconPack.Ribbon,
                    contentDescription = "ribbon"
                )
            }

            val size = 52
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.size(size.dp))
                Icon(
                    modifier = Modifier.size(size.dp),
                    painter = painterResource(id = firtStep.iconPainter),
                    contentDescription = "test",
                    tint = firtStep.iconColor
                )
                Icon(
                    modifier = Modifier.size(size.dp),
                    painter = painterResource(id = secondStep.iconPainter),
                    contentDescription = "test",
                    tint = secondStep.iconColor
                )
                Icon(
                    modifier = Modifier.size(size.dp),
                    painter = painterResource(id = thirdStep.iconPainter),
                    contentDescription = "test",
                    tint = thirdStep.iconColor
                )
                Icon(
                    modifier = Modifier.size(size.dp),
                    painter = painterResource(id = fourthStep.iconPainter),
                    contentDescription = "test",
                    tint = fourthStep.iconColor
                )
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column() {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            circleIcon()
                            circleIcon()
                            circleIcon()
                            circleIcon()
                        }
                        Box(
                            modifier = Modifier
                                .size(height = 12.dp, width = progressPercent.dp)
                                .background(color = Point.copy(alpha = 0.8f), shape = CircleShape)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        TextFormat(text = firtStep.state, size = 8)
                        TextFormat(text = secondStep.state, size = 8)
                        TextFormat(text = thirdStep.state, size = 8)
                        TextFormat(text = fourthStep.state, size = 8)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProgressBarPager(progressBar: MutableList<ProgressBar>) {
    val nowImageIndex = rememberPagerState(0)
    val circle = painterResource(id = R.drawable.circle)

    var indexIcons: List<Painter> = listOf()

    repeat(progressBar.size) {
        indexIcons = indexIcons.plus(circle)
    }

    if (progressBar.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Box(
                modifier = Modifier
                    .size(height = 200.dp, width = 370.dp)
                    .background(ProgressBGColor, shape = RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = painterResource(id = R.drawable.tree), contentDescription = "tree")
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
                        TextFormat(text = "Re:Born과 함께 \n옷에 새 생명을 주세요!!", size = 16, fontWeight = FontWeight.Bold)
                        TextFormat(text = "의류 폐기물들의 처리 과정에서\n환경이 파괴되고 있어요! \nRe:Born과 옷에 새 생명을 주고\n지구를 지켜요", size = 12)
                    }

                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = progressBar.size,
            state = nowImageIndex
        ) { page ->
            ProgressBar("박동민", progressBar[page].count, progressBar[page].donationStatus)
        }
        Row(modifier = Modifier.padding(3.dp)) {
            indexIcons.forEachIndexed { index, icon ->
                Icon(
                    modifier = Modifier
                        .size(15.dp)
                        .padding(2.dp),
                    painter = icon,
                    contentDescription = "Index Icon",
                    tint = if (index == nowImageIndex.currentPage) Point
                    else Color.LightGray
                )
            }
        }

    }
}

data class ProgressStep(
    var state: String = "Error",
    var color: Color = Color.Gray,
    var iconPainter: Int = R.drawable.shopping_outline,
    var iconColor: Color = ProgressBGColor
)

@Preview
@Composable
fun PreviewProgressBar1() {
    ProgressBar("박동민", 2, "접수완료")
}

@Preview
@Composable
fun PreviewProgressBar2() {
    ProgressBar("박동민", 2, "수거중")
}

@Preview
@Composable
fun PreviewProgressBar3() {
    ProgressBar("박동민", 2, "검수중")
}

@Preview
@Composable
fun PreviewProgressBar4() {
    ProgressBar("박동민", 2, "리폼중")
}

@Preview
@Composable
fun PreviewProgressBar5() {
    ProgressBar("박동민", 2, "판매중")
}

@Preview
@Composable
fun PreviewProgressBar6() {
    ProgressBar("박동민", 2, "기부완료")
}
