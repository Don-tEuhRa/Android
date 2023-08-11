package com.dongminpark.reborn.Screens

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dongminpark.foodmarketandroid.navigation.Screen
import com.dongminpark.reborn.App
import com.dongminpark.reborn.ui.theme.Point
import com.dongminpark.reborn.R
import com.dongminpark.reborn.Retrofit.RetrofitManager
import com.dongminpark.reborn.Utils.Constants.TAG
import com.dongminpark.reborn.Utils.MESSAGE
import com.dongminpark.reborn.Utils.OAuthData
import com.dongminpark.reborn.Utils.RESPONSE_STATE
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun LoginScreen(navController: NavHostController) {
    navController.enableOnBackPressed(false)
    var isLoginLoading by remember {
        mutableStateOf(false)
    }

    if (isLoginLoading){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
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
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                            OAuthData.nav?.navigate(Screen.Once.route)
                            //isLoginLoading = true
                            //googleLogin()
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
                Log.e("uid", uid) // BE가 보내달라함

                RetrofitManager.instance.firebaseConnect(uid, completion = {
                        responseState ->
                    when (responseState) {
                        RESPONSE_STATE.OKAY -> {
                            //OAuthData.nav?.navigate(Screen.Once.route)
                            Log.d(TAG, "api 호출 성공")
                        }
                        RESPONSE_STATE.FAIL -> {
                            //isLoginLoading = false
                            Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "api 호출 에러")
                        }
                    }
                })
            }
            else{
                //isLoginLoading = false
                //Toast.makeText(App.instance, MESSAGE.ERROR, Toast.LENGTH_SHORT).show()
                Log.e("에러 : ", "${task.exception}")
                Log.e("Firebase ERROR", "먼가 먼가 잘못됨")
            }
        }
}

fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
    try {
        val account = completedTask.getResult(ApiException::class.java)
        val serviceAccountKey = account.serverAuthCode.toString()
        val email = account?.email.toString()
        val name = account?.displayName.toString()
        val profileImg = account?.photoUrl
        var userId = account?.id.toString()
        var accessToken = account?.idToken.toString()

        Log.e("Google account email", email)
        Log.e("Google account name", name)
        Log.e("Google account profileImg", "$profileImg")
        Log.e("Google account userId", userId)
        Log.e("Google account serviceAccountKey", serviceAccountKey)
        Log.e("Google account accessId", accessToken)

        firebaseAuthWithGoogle(account)

    } catch (e: ApiException) {
        //isLoginLoading = false
        Log.e("Google account", "signInResult:failed Code = " + e.statusCode)
    }
}

fun googleLogin() {
    var signIntent: Intent = OAuthData.mGoogleSignInClient!!.getSignInIntent()
    OAuthData.GoogleSignResultLauncher.launch(signIntent)
}