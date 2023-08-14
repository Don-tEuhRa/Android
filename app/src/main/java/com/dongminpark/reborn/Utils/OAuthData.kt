package com.dongminpark.reborn.Utils

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class OAuthData {
    companion object {
        val ID = "375241323724-9gdibtcgnr90rr6iqekm6ku2srbfph6a.apps.googleusercontent.com"
        lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>
        var auth : FirebaseAuth? = null
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ID)
            .requestServerAuthCode(ID)
            .requestProfile()
            .requestEmail()
            .requestId()
            .build()
        var mGoogleSignInClient: GoogleSignInClient? = null
        var account : GoogleSignInAccount? = null
        var nav : NavHostController? = null

        // 임시로 amdin token 사용. 추후 변경 예정.
        var ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsInJvbGUiOiJVU0VSIiwicHJvdmlkZXIiOiJHT09HTEUiLCJuYW1lIjoiWzMgOiDrsJXrj5nrr7xdIiwidXNlcklkIjoiMyIsImVtYWlsIjoicGRtMDAxMTI1QGdtYWlsLmNvbSIsImlhdCI6MTY5MTkzOTk3NCwiZXhwIjoxNjkyMDI2Mzc0fQ.TqzLoCgJ9saXp1Y7onuUVxCWUfdVe5MPNpOhtUIpxps"
    }
}