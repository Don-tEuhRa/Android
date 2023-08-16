package com.dongminpark.reborn.Frames

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongminpark.reborn.Buttons.FavoriteButton
import com.dongminpark.reborn.Model.Product

@Composable
fun productFrame(
    product: Product,
    navController: NavController,
) {
    var isFavorite by remember { mutableStateOf(product.isInterested) }
    Box(
        modifier = Modifier
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.clickable {
                navController.navigate("storeDetail/${product.productId}")
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(0.dp, Color.Transparent),
                contentAlignment = Alignment.BottomEnd
            ) {
                ImageFormat(url = product.thumbnailUrl)
                Box(modifier = Modifier.padding(8.dp)) {
                    FavoriteButton(isFavorite = isFavorite){
                        isFavorite = !isFavorite
                        if(isFavorite){
                            // 관심상품 등록
                        }else{
                            // 관심상품 해제
                        }
                    }
                }
            }
            Text(
                text = product.title,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "${product.price}원",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}