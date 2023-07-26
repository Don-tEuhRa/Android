package com.dongminpark.reborn.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dongminpark.reborn.R

val customFamily = FontFamily(
    Font(R.font.spopahansneo_thin, FontWeight.Thin),
    Font(R.font.spopahansneo_light, FontWeight.Light),
    Font(R.font.spopahansneo_medium, FontWeight.Medium),
    Font(R.font.spopahansneo_bold, FontWeight.Bold),
    Font(R.font.spopahansneo_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = customFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)