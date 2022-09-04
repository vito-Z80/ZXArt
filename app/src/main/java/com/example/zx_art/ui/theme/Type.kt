package com.example.zx_art.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.unit.sp
import com.example.zx_art.R

val zxFont = FontFamily(Font(R.font.zxspectr, weight = FontWeight.Normal, style = FontStyle.Normal))

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = zxFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16f.sp,
        color = Color.White,
        textGeometricTransform = TextGeometricTransform(scaleX = 0.75f)
    ),
    h2 = TextStyle(
        fontFamily = zxFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16f.sp,
        color = Color.Black
    ),
    h3 = TextStyle(
        fontFamily = zxFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32f.sp,
        color = Color.Black
    ),
    button = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
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