package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dongminpark.reborn.Buttons.*
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreDetailScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3, 4)) }
    val name = "반팔티셔츠"
    val price = 10000
    LazyColumn(
        modifier = Modifier
        //.padding(bottom = 16.dp)
    ) {
        item {
            TopAppBar(
                backgroundColor = Color.White,
                contentPadding = PaddingValues(8.dp)
            ) {
                ReBorn()
                Spacer(modifier = Modifier.weight(1f))
                Box(contentAlignment = Alignment.CenterEnd) {
                    Row {
                        FavoriteListButton{navController.navigate("storeLikeList")}
                        Spacer(modifier = Modifier.weight(1f))
                        ShoppingCart{navController.navigate("storeShoppingCart")}
                    }
                }
            }
            Divider(color = Color.Black, thickness = 1.dp)
        }

        item {
            // 사진 Pager로 표시 및 현재 페이지 표시
            PostUi(images = itemList)
        }

        item {
            // 상품 이름, 가격, 버튼 3개 Row
            Row(modifier = Modifier.fillMaxWidth().padding(4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                ) {
                    TextFormat(text = name, size = 36)
                    Spacer(modifier = Modifier.size(16.dp))
                    TextFormat(text = "${price}원", size = 24)
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextButtonFormat(modifier = Modifier.padding(8.dp),heightSize = 40, widthSize = 132, shape = RoundedCornerShape(12.dp), text = "장바구니 담기") {
                        // api 어쩌구~
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        FavoriteButton()
                        TextButtonFormat(modifier = Modifier.padding(8.dp), heightSize = 40, widthSize = 100, shape = RoundedCornerShape(12.dp), text = "구매하기") {
                            // api 어쩌구~
                        }
                    }
                }

            }
            Divider(color = Color.Black, thickness = 1.dp)
        }

        item {
            val painter =
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = R.drawable.detail_contents)
                        .apply(block = fun ImageRequest.Builder.() {
                            // 이미지 로드 중 및 실패 시 표시할 이미지 리소스를 설정할 수 있습니다.
                            placeholder(R.drawable.placeholder)
                            error(R.drawable.placeholder)
                        }).build()
                )
            Image(
                contentScale = ContentScale.FillBounds,
                painter = painter,
                contentDescription = "Image",
                modifier = Modifier
                    .fillMaxSize()//Width()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostUi(images: MutableList<Int>) {
    val nowImageIndex = rememberPagerState(0)
    val circle = painterResource(id = R.drawable.circle)
    // 사진 갯수따라 동적인 변화 필요
    var indexIcons: List<Painter> = listOf()

    repeat(images.size) {
        indexIcons = indexIcons.plus(circle)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = images.size,
            state = nowImageIndex
        ) { page ->
            Image(
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()//Width()
            )
        }
        Row(modifier = Modifier.padding(3.dp)) {
            indexIcons.forEachIndexed { index, icon ->
                Icon(
                    modifier = Modifier
                        .size(15.dp)
                        .padding(2.dp),
                    painter = icon,
                    contentDescription = "Index Icon",
                    tint = if (index == nowImageIndex.currentPage) MaterialTheme.colors.primaryVariant
                    else MaterialTheme.colors.secondary
                )
            }
        }

    }
}


@Preview
@Composable
fun StoreDetailScreenPreview() {
    val navController = rememberNavController()
    StoreDetailScreen(navController)
}