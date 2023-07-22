package com.dongminpark.reborn.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dongminpark.foodmarketandroid.navigation.Screen
import com.dongminpark.reborn.Screens.*
import com.dongminpark.reborn.navigation.*
import com.dongminpark.reborn.Screens.LoginScreen
import java.lang.Exception


@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Once.route) {
            OnceScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        BottomScreen.Main,
        BottomScreen.Donate,
        BottomScreen.Store,
        BottomScreen.My
    )

    BottomNavigation(backgroundColor = Color.DarkGray) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = if (currentRoute?.startsWith(item.screenRoute) == true) item.iconSolid else item.iconOutline,
                        contentDescription = item.title,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                label = { Text(item.title, fontSize = 11.sp) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White,
                selected = currentRoute?.startsWith(item.screenRoute) == true,
                alwaysShowLabel = false,
                onClick = {
                    try {
                        navController.navigate(item.screenRoute) {
                            if (currentRoute?.startsWith(item.screenRoute) != true) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) { saveState = true }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    } catch (e: Exception) {
                        navController.navigate(item.screenRoute)
                    }
                }
            )
        }
    }
}

@Composable
fun MainScreenView(startDestination: String) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        Box(Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                // Main
                composable(MainNavigationScreens.Main.route) {
                    MainScreen(navController = navController)
                }

                // Donate
                composable(DonateNavigationScreens.Donate.route) {
                    DonateScreen(navController = navController)
                }

                // Store
                composable(StoreNavigationScreens.Store.route) {
                    StoreScreen(navController = navController)
                }

                // My
                composable(MyNavigationScreens.My.route) {
                    MyScreen(navController = navController)
                }
            }
        }
    }
}