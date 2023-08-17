package com.dongminpark.reborn.Screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dongminpark.reborn.App
import com.dongminpark.reborn.ui.theme.Point
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.API
import com.dongminpark.reborn.Utils.MESSAGE
import com.dongminpark.reborn.Utils.OAuthData
import com.dongminpark.reborn.Utils.RESPONSE_STATE
import com.dongminpark.reborn.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

var isLoginLoading by mutableStateOf(false)

@Composable
fun LoginScreen(navController: NavHostController) {
    navController.enableOnBackPressed(false)

    if (isLoginLoading){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Point),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Loading...",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 100.dp)
            )
        }
    }else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Point),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                /* // 이미지 받는 주소 양식 예시
                val painter = // 이미지 로드 중 및 실패 시 표시할 이미지 리소스를 설정할 수 있습니다.
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = API.BASE_URL + "/resources/files/318991937141700_0c2a924b-e262-4c76-a2bf-b3d1dc756bf4.jpg").apply(block = fun ImageRequest.Builder.() {
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
                        .aspectRatio(1f)
                        .fillMaxSize()//Width()
                        .clip(RoundedCornerShape(12.dp))
                )
                 */
                Image(
                    painter = painterResource(id = R.drawable.ribbon),
                    contentDescription = "리본 아이콘",
                    modifier = Modifier.size(320.dp)
                )
                Text(
                    text = "옷의 가치를 더하다",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color.Black,
                )
                Text(
                    text = "Re:Born",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color.Black,
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "SNS계정으로 로그인하기",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Icon(
                    painter = painterResource(id = R.drawable.google_kor),
                    contentDescription = "Google Login",
                    modifier = Modifier
                        .clickable {
                            //isLoginLoading = true
                            //googleLogin()
                            OAuthData.nav?.navigate(Screen.Once.route)
                        }
                        .padding(10.dp)
                        .width(240.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
    Log.e("Firebase", "진입 성공")
    var credntial = GoogleAuthProvider.getCredential(account?.idToken, null)

    OAuthData.auth?.signInWithCredential(credntial)
        ?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                var uid = OAuthData.auth?.currentUser?.uid.toString()

                Log.e("Firebase Success", "네 성공했습니다.")

                RetrofitManager.instance.firebaseConnect(uid, completion = {
                        responseState ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            OAuthData.nav?.navigate(Screen.Once.route)
                            isLoginLoading = false
                        }
                        RESPONSE_STATE.FAIL -> {
                            isLoginLoading = false
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            else{
                isLoginLoading = false
                Log.e("ERROR", "firebaseAuthWithGoogle: error", )
                Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
            }
        }
}

fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
        val account = completedTask.getResult(ApiException::class.java)

        firebaseAuthWithGoogle(account)
    } catch (e: ApiException) {
        //isLoginLoading = false
    }
}

fun googleLogin() {
    var signIntent: Intent = OAuthData.mGoogleSignInClient!!.getSignInIntent()
    OAuthData.GoogleSignResultLauncher.launch(signIntent)
}