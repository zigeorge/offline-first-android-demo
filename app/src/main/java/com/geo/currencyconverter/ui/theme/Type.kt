package com.geo.currencyconverter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.geo.currencyconverter.R

private val fontFamilyRoboto = FontFamily(
    listOf(
        Font(
            resId = R.font.roboto_regular
        ),
        Font(
            resId = R.font.roboto_light,
            weight = FontWeight.Light
        )
    )
)

private val fontFamilyNotoSans = FontFamily(
    listOf(
        Font(
            resId = R.font.notosans_regular
        ),
        Font(
            resId = R.font.notosans_bold,
            weight = FontWeight.Bold
        )
    )
)

val typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        letterSpacing = (1.15).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontSize = 15.sp,
        letterSpacing = (1.15).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamilyNotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilyNotoSans,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamilyNotoSans,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyNotoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = (1.15).sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontSize = 12.sp,
        letterSpacing = (1.15).sp
    ),

)
