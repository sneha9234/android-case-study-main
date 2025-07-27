package com.target.targetcasestudy.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.target.targetcasestudy.R

val RobotoFontFamily = try {
    FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_bold, FontWeight.Bold)
    )
} catch (e: Exception) {
    // Fallback to system sans-serif (closest to Roboto) if font files are missing
    FontFamily.SansSerif
} 