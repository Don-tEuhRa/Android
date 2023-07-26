package com.dongminpark.reborn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Navigation.SetupNavGraph
import com.dongminpark.reborn.Screens.handleSignInResult
import com.dongminpark.reborn.Utils.OAuthData
import com.dongminpark.reborn.ui.theme.ReBornTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //OAuthData.auth = FirebaseAuth.getInstance() // 파이어베이스 연동 후 사용 가능
        OAuthData.GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
        installSplashScreen()
        setContent {
            ReBornTheme {
                val navController = rememberNavController()
                OAuthData.nav = navController
                OAuthData.mGoogleSignInClient = GoogleSignIn.getClient(this, OAuthData.gso)
                OAuthData.account = GoogleSignIn.getLastSignedInAccount(this)
                SetupNavGraph(navController = navController)
            }
        }
    }
}