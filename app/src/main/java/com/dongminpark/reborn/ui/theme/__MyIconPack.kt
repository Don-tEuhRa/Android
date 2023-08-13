package com.dongminpark.reborn.ui.theme

import androidx.compose.ui.graphics.vector.ImageVector
import com.dongminpark.reborn.ui.theme.myiconpack.CallOutline
import com.dongminpark.reborn.ui.theme.myiconpack.Check
import com.dongminpark.reborn.ui.theme.myiconpack.CheckcircleOutline
import com.dongminpark.reborn.ui.theme.myiconpack.CheckcircleSolid
import com.dongminpark.reborn.ui.theme.myiconpack.Gift
import com.dongminpark.reborn.ui.theme.myiconpack.GiftOutline
import com.dongminpark.reborn.ui.theme.myiconpack.GiftSolid
import com.dongminpark.reborn.ui.theme.myiconpack.HeartOutline
import com.dongminpark.reborn.ui.theme.myiconpack.HeartSolid
import com.dongminpark.reborn.ui.theme.myiconpack.HomeOutline
import com.dongminpark.reborn.ui.theme.myiconpack.HomeSolid
import com.dongminpark.reborn.ui.theme.myiconpack.Ribbon
import com.dongminpark.reborn.ui.theme.myiconpack.SearchOnly
import com.dongminpark.reborn.ui.theme.myiconpack.SearchOutline
import com.dongminpark.reborn.ui.theme.myiconpack.ShopOutline
import com.dongminpark.reborn.ui.theme.myiconpack.ShopSolid
import com.dongminpark.reborn.ui.theme.myiconpack.ShoppingOutline
import com.dongminpark.reborn.ui.theme.myiconpack.ThumbUpSolid
import com.dongminpark.reborn.ui.theme.myiconpack.Truck
import com.dongminpark.reborn.ui.theme.myiconpack.TruckFilled
import com.dongminpark.reborn.ui.theme.myiconpack.UserOutline
import com.dongminpark.reborn.ui.theme.myiconpack.UserSolid
import com.dongminpark.reborn.ui.theme.myiconpack.X
import kotlin.collections.List as ____KtList

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(SearchOnly, X, ThumbUpSolid, HeartSolid, GiftSolid, CheckcircleSolid,
        UserOutline, Check, HeartOutline, ShopSolid, ShopOutline, Truck, GiftOutline,
        CheckcircleOutline, TruckFilled, ShoppingOutline, UserSolid, Gift, CallOutline, HomeOutline,
        HomeSolid, SearchOutline, Ribbon)
    return __AllIcons!!
  }
