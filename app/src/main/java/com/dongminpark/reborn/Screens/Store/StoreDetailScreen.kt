package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Buttons.*
import com.dongminpark.reborn.Frames.ImageFormat
import com.dongminpark.reborn.Frames.TextFormat
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Screens.Store.PayCartItemList
import com.dongminpark.reborn.Utils.Constants
import com.dongminpark.reborn.Utils.LoadingCircle
import com.dongminpark.reborn.Utils.MESSAGE
import com.dongminpark.reborn.Utils.RESPONSE_STATE
import com.dongminpark.reborn.ui.theme.Point
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreDetailScreen(navController: NavController, productId: Int) {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isPaying by remember { mutableStateOf(false) }
    var productImageUrl by rememberSaveable { mutableStateOf(listOf("")) }
    var productTitle by rememberSaveable { mutableStateOf( "") }
    var productPrice by rememberSaveable { mutableStateOf( "") }
    var productContent by rememberSaveable { mutableStateOf( "") }
    var productIsInterested by rememberSaveable { mutableStateOf(false) }

    if (isPaying){
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("잠시만 기다려 주세요")
            },
            text = {
                Text("결제 페이지로 이동중입니다")
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

    if (isLoading){
        LoadingCircle()
        RetrofitManager.instance.productShowID(
            productId,
            completion = { responseState, product->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        productImageUrl = product!!.imageUrl
                        productTitle = product.title
                        productPrice = product.price.toString()
                        productContent = product.content
                        productIsInterested = product.isInterested
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }else {
        LazyColumn(
            modifier = Modifier
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
                            FavoriteListButton { navController.navigate("storeLikeList") }
                            Spacer(modifier = Modifier.width(12.dp))
                            ShoppingCart { navController.navigate("storeShoppingCart") }
                        }
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }

            item {
                PostUi(images = productImageUrl)
            }

            item {
                var isFavorite by remember { mutableStateOf(productIsInterested) }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        TextFormat(text = productTitle, size = 36)
                        Spacer(modifier = Modifier.size(16.dp))
                        TextFormat(text = "${productPrice}원", size = 24)
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextButtonFormat(
                            modifier = Modifier.padding(8.dp),
                            heightSize = 40,
                            widthSize = 136,
                            textColor = Color.White,
                            backgroundColor = Point,
                            borderColor = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            text = "장바구니 담기"
                        ) {
                            Toast.makeText(
                                App.instance,
                                "장바구니에 담는중...",
                                Toast.LENGTH_SHORT
                            ).show()

                            RetrofitManager.instance.cartCreate(
                                productId,
                                completion = { responseState->

                                    when (responseState) {
                                        RESPONSE_STATE.OKAY -> {
                                            Toast.makeText(
                                                App.instance,
                                                "장바구니에 담겼습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        RESPONSE_STATE.FAIL -> {
                                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                })
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FavoriteButton(isFavorite){
                                isFavorite = !isFavorite
                                if(isFavorite){
                                    // 관심상품 등록
                                    RetrofitManager.instance.interestedSave(
                                        productId = productId,
                                        completion = { responseState ->

                                            when (responseState) {
                                                RESPONSE_STATE.OKAY -> {
                                                    Toast.makeText(App.instance, "좋아요 목록에 추가되었습니다", Toast.LENGTH_SHORT).show()
                                                }
                                                RESPONSE_STATE.FAIL -> {
                                                    Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        })
                                }else{
                                    // 관심상품 해제
                                    RetrofitManager.instance.interestedDelete(
                                        productId = productId,
                                        completion = { responseState ->

                                            when (responseState) {
                                                RESPONSE_STATE.OKAY -> {
                                                    Toast.makeText(App.instance, "좋아요 목록에서 삭제되었습니다", Toast.LENGTH_SHORT).show()
                                                }
                                                RESPONSE_STATE.FAIL -> {
                                                    Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        })
                                }
                            }
                            TextButtonFormat(
                                modifier = Modifier.padding(8.dp),
                                heightSize = 40,
                                widthSize = 100,
                                textColor = Color.White,
                                backgroundColor = Point,
                                borderColor = Color.White,
                                shape = RoundedCornerShape(12.dp),
                                text = "구매하기"
                            ) {
                                isPaying = true
                                RetrofitManager.instance.productShowID(
                                    productId,
                                    completion = { responseState, product->

                                        when (responseState) {
                                            RESPONSE_STATE.OKAY -> {
                                                PayCartItemList.clear()
                                                PayCartItemList.addAll(arrayListOf(product!!))
                                                isPaying = false
                                                navController.navigate("storePay")
                                            }
                                            RESPONSE_STATE.FAIL -> {
                                                Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    })
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
                            .data(data = productContent)
                            .apply(block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.detail_image)
                                error(R.drawable.detail_image)
                            }).build()
                    )
                Image(
                    contentScale = ContentScale.FillHeight,
                    painter = painter,
                    contentDescription = "Image",
                    modifier = Modifier
                        .aspectRatio(1 / 14f)
                        .fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostUi(images: List<String>) {
    val nowImageIndex = rememberPagerState(0)
    val circle = painterResource(id = R.drawable.circle)
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
            ImageFormat(Modifier, images[page])
        }
        if (images.size > 1) {
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
}

@Preview
@Composable
fun StoreDetailScreenPreview() {
    val navController = rememberNavController()
    StoreDetailScreen(navController, 1)
}