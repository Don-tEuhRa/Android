package com.dongminpark.reborn.Navigation

import android.util.Log
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dongminpark.reborn.Screens.*
import com.dongminpark.reborn.navigation.*
import com.dongminpark.reborn.Screens.LoginScreen
import com.dongminpark.reborn.Screens.Store.StoreLikeListScreen
import com.dongminpark.reborn.Screens.Store.StorePayAfterScreen
import com.dongminpark.reborn.Screens.Store.StorePayScreen
import com.dongminpark.reborn.Screens.Store.StoreShoppingCartScreen
import com.dongminpark.reborn.Utils.MainContents
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
                    MainScreen(modifier = Modifier, introductions = MainContents.introMain,navController = navController)
                }

                // Donate
                composable(DonateNavigationScreens.Donate.route) {
                    DonateScreen(navController = navController)
                }

                // Store
                composable(StoreNavigationScreens.Store.route) {
                    StoreScreen(navController = navController)
                }
                composable(
                    route = "${StoreNavigationScreens.StoreDetail.route}/{variable}",
                    arguments = listOf(navArgument("variable") { type = NavType.StringType })
                ) { entry ->
                    val variable = entry.arguments?.getString("variable")
                    Log.e("TAG", "MainScreenView: ${variable}", )
                    StoreDetailScreen(
                        navController = navController,
                        productId = variable!!.toInt()
                    )
                }
                composable(StoreNavigationScreens.StoreLikeList.route) {
                    StoreLikeListScreen(navController = navController)
                }
                composable(StoreNavigationScreens.StorePay.route) {
                    StorePayScreen(navController = navController)
                }
                composable(
                    route = "${StoreNavigationScreens.StorePayAfter.route}/{name}/{phone}/{address}/{point}",
                    arguments = listOf(
                        navArgument("name") { type = NavType.StringType },
                        navArgument("phone") { type = NavType.StringType },
                        navArgument("address") { type = NavType.StringType },
                        navArgument("point") { type = NavType.StringType }
                    )
                ) {entry ->
                    val name = entry.arguments?.getString("name")
                    val phone = entry.arguments?.getString("phone")
                    val address = entry.arguments?.getString("address")
                    val point = entry.arguments?.getString("point")

                    StorePayAfterScreen(navController = navController, name!!, phone!!, address!!, point!!)
                }
                composable(StoreNavigationScreens.StoreShoppingCart.route) {
                    StoreShoppingCartScreen(navController = navController)
                }

                // My
                composable(MyNavigationScreens.My.route) {
                    MyScreen(navController = navController)
                }
                composable(MyNavigationScreens.MyOrder.route) {
                    myOrderPage()
                }
                composable(MyNavigationScreens.MyDonate.route) {
                    myDonatePage()
                }
                composable(MyNavigationScreens.MyQnA.route) {
                    MyQnAScreen(navController = navController)
                }
            }
        }
    }
}