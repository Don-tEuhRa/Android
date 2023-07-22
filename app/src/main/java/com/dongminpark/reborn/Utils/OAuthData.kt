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
        val ID = "457162263965-72ocfb6ba7u6p43jgeqles1hn9np3u45.apps.googleusercontent.com"
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
        var ACCESS_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b2tlbiIsInVpZCI6IjRyOUpYam1zVHRZVjBONGl4UlRTU0NrM0pJSDIiLCJyb2xlIjoiVVNFUiIsInByb3ZpZGVyIjoiR09PR0xFIiwicHJvZmlsZUltZ1VybCI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FBY0hUdGRrVTRNMkFrSTliOVh0ZnVRcHVuam9XSFRHMlhiR2ZxZHdaZlo0ZkhuWVlSaz1zOTYtYyIsIm5hbWUiOiJbMSA6IOuwleuPmeuvvF0iLCJ1c2VySWQiOiIxIiwiaWF0IjoxNjg5MzQzMzcxLCJleHAiOjE2ODk0Mjk3NzF9.9N-TQTDuDcoaKuRdxvtaiLA8uvx9C8YK--ZoBlTIBPY"
    }
}