package com.dongminpark.reborn.Screens.Store

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dongminpark.reborn.Frames.FinalPayPriceFrame
import com.dongminpark.reborn.Frames.ItemInfoFrame
import com.dongminpark.reborn.Frames.UserInfoFrame

@Composable
fun StorePayAfterScreen(navController: NavController) {
    val itemList by remember { mutableStateOf(mutableListOf(1, 2, 3)) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        //verticalArrangement = Arrangement.SpaceAround
    ){
        item{
            UserInfoFrame(name = "박동민", phone = "010-2245-3683", address = "수원시 장안구 연무동 123-456")
        }
        item {
            Spacer(modifier = Modifier.padding(12.dp))
        }

        item {
            ItemInfoFrame(itemList = itemList)
        }
        item {
            Spacer(modifier = Modifier.padding(12.dp))
        }
        item {
            FinalPayPriceFrame(12000, 1200)
        }
    }
}

@Preview
@Composable
fun StorePayAfterScreenPreview() {
    StorePayAfterScreen(navController = rememberNavController())
}