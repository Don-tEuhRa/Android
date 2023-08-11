package com.dongminpark.reborn.Utils

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.R
import com.dongminpark.reborn.ui.theme.Point
import com.dongminpark.reborn.ui.theme.ProgressBGColor

@Composable
fun ProgressBar(name: String, count: Int, progress: String) {
    var firtStep: ProgressStep = ProgressStep("수거중")
    var secondStep: ProgressStep = ProgressStep("검수중")
    var thirdStep: ProgressStep = ProgressStep("리폼중")
    var fourthStep: ProgressStep = ProgressStep("판매중")
    var state: String = "에러"
    var progressPercent = 0

    when (progress) {
        "접수완료" -> {
            state = "리본을 기다리는 중"
        }
        "수거중" -> {
            state = "여행하는 중"
            firtStep = ProgressStep("수거 완료", Point, R.drawable.shopping_outline, Color.White)
            progressPercent = 75
        }
        "검수중" -> {
            state = "꿈을 찾는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 완료", Point, R.drawable.shopping_outline, Color.White)
            progressPercent = 140
        }
        "리폼중" -> {
            state = "꽃단장 하는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 완료", Point)
            thirdStep = ProgressStep("리폼 완료", Point, R.drawable.shopping_outline, Color.White)
            progressPercent = 210
        }
        "판매중" -> {
            state = "새로운 주인을 기다리는 중"
            firtStep = ProgressStep("수거 완료", Point)
            secondStep = ProgressStep("검수 완료", Point)
            thirdStep = ProgressStep("리폼 완료", Point)
            fourthStep = ProgressStep("판매 완료", Point, R.drawable.shopping_outline, Color.White)
            progressPercent = 265
        }
        "기부완료" -> {
            state = "${name}님의 따뜻한 마음을 전달했어요"
            progressPercent = 280
        }
    }

    Box(
        modifier = Modifier
            .size(height = 160.dp, width = 320.dp)
            .border(1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
            .background(ProgressBGColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                .size(height = 36.dp, width = 280.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextFormat(text = "${name}님이 ${count}번째 기부하신 물건이\n${state} ~!!", size = 12)
                Icon(modifier = Modifier.size(32.dp), painter = painterResource(id = R.drawable.heart_filled), contentDescription = "heart_filled")
            }

            val size = 30
            Row(modifier = Modifier.size(height = 32.dp, width = 300.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Spacer(modifier = Modifier.size(size.dp))
                //Icon(modifier = Modifier.size(size.dp), painter = painterResource(id = R.drawable.shopping_outline), contentDescription = "test", tint = ProgressBGColor)
                Icon(modifier = Modifier.size(size.dp), painter = painterResource(id = firtStep.iconPainter), contentDescription = "test", tint = firtStep.iconColor)
                Icon(modifier = Modifier.size(size.dp), painter = painterResource(id = secondStep.iconPainter), contentDescription = "test", tint = secondStep.iconColor)
                Icon(modifier = Modifier.size(size.dp), painter = painterResource(id = thirdStep.iconPainter), contentDescription = "test", tint = thirdStep.iconColor)
                Icon(modifier = Modifier.size(size.dp), painter = painterResource(id = fourthStep.iconPainter), contentDescription = "test", tint = fourthStep.iconColor)
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column() {
                    Box(
                        modifier = Modifier
                            .size(height = 12.dp, width = 280.dp)
                            .background(Color.White, shape = CircleShape)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "circle",
                                tint = Color.Gray
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "circle",
                                tint = Color.Gray
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "circle",
                                tint = Color.Gray
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.circle),
                                contentDescription = "circle",
                                tint = Color.Gray
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(height = 12.dp, width = progressPercent.dp)
                                .background(color = Point.copy(alpha = 0.8f), shape = CircleShape)
                        )
                    }
                    Row(
                        modifier = Modifier.size(height = 12.dp, width = 280.dp),
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
