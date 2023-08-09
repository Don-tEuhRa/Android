package com.dongminpark.reborn.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreen(
    val title: String,
    val iconOutline: ImageVector,
    val iconSolid: ImageVector,
    val screenRoute: String
) {
    object Main : BottomScreen("리본", Icons.Outlined.Home, Icons.Filled.Home, "main")

    object Donate :
        BottomScreen("기부하기", Icons.Outlined.Email, Icons.Filled.Email, "donate")

    object Store : BottomScreen("스토어", Icons.Outlined.Menu, Icons.Filled.Menu, "store")

    object My :
        BottomScreen("마이메뉴", Icons.Outlined.Person, Icons.Filled.Person, "my")
}

sealed class MainNavigationScreens(val route: String) {
    object Main : MainNavigationScreens("main")
}

sealed class DonateNavigationScreens(val route: String) {
    object Donate : DonateNavigationScreens("donate")
}

sealed class StoreNavigationScreens(val route: String) {
    object Store : StoreNavigationScreens("store")
    object StoreDetail : StoreNavigationScreens("storeDetail")
    object StoreLikeList : StoreNavigationScreens("storeLikeList")
    object StoreShoppingCart : StoreNavigationScreens("storeShoppingCart")
    object StorePay : StoreNavigationScreens("storePay")
    object StorePayAfter : StoreNavigationScreens("storePayAfter")
}

sealed class MyNavigationScreens(val route: String = "my") {
    object My : MyNavigationScreens("my")
}