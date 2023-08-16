package com.dongminpark.reborn.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.dongminpark.reborn.App
import com.dongminpark.reborn.Model.ProgressBar
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.*
import com.dongminpark.reborn.ui.theme.ReBornTheme


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

var progressBar = mutableListOf<ProgressBar>()

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

    if (isLoading){
        LoadingCircle()
        RetrofitManager.instance.progressList(
            completion = { responseState, progress ->

                when (responseState) {
                    RESPONSE_STATE.OKAY -> {
                        Log.d(Constants.TAG, "api 호출 성공")
                        progressBar = progress!!
                        isLoading = false
                    }
                    RESPONSE_STATE.FAIL -> {
                        Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        Log.d(Constants.TAG, "api 호출 에러")
                    }
                }
            })
    }else{
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
                    introductions.forEach { introduction ->
                        introductionView(aIntro = introduction, navController = navController as NavHostController)
                    }
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
                        Icon(painter = painterResource(id = aIntro.icon), contentDescription = "Icon", Modifier.size(36.dp))
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


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ReBornTheme {
        val navController = rememberNavController()
        MainScreen(modifier = Modifier, introductions = MainContents.introMain,navController)

    }
}

