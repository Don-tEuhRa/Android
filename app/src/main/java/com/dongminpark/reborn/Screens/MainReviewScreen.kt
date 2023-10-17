package com.dongminpark.reborn.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.Review
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.LoadingCircle
import com.dongminpark.reborn.Utils.MESSAGE
import com.dongminpark.reborn.Utils.RESPONSE_STATE

@Composable
fun MainReviewScreen() {
    var reviews by rememberSaveable { mutableStateOf(MutableList(0) { Review() }) }

    var isLoading by rememberSaveable {
        mutableStateOf(true)
    }

    if (isLoading) {
        LoadingCircle()
        RetrofitManager.instance.reviewList(
            completion = { responseState, review ->
                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        reviews = review!!
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            ReviewTopAppBar()
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn {
                items(reviews) {
                    SatisfactionBar(
                        modifier = Modifier.padding(4.dp),
                        name = it.userName,
                        content = it.content,
                        star = it.star,
                        date = it.createdAt
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewTopAppBar() {
    var opened by remember {
        mutableStateOf(false)
    }
    if (opened) {
        // dialog 띄우기
        reviewBar()
    }

    TopAppBar(
        elevation = 10.dp,
        backgroundColor = Color(0xff78C1F3),
        modifier = Modifier.height(60.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Review",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "리뷰 남기기>",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    modifier = Modifier.clickable {
                        opened = !opened
                    }
                )
            }
        }
    }
}