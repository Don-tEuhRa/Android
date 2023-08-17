package com.dongminpark.reborn.Frames

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dongminpark.reborn.R

@Composable
fun ImageFormat(modifier: Modifier = Modifier, url: String, size: Int = 0){
    Box (
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
    ){
        val painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder)
                }).build()
            )
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painter,
            contentDescription = "Image",
            modifier = if (size == 0) {
                Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            } else {
                Modifier
                    .size(size.dp)
                    .clip(RoundedCornerShape(12.dp))
            }
        )
    }
}