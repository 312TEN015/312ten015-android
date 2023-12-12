package com.fourleafclover.tarot.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_semibold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal)
)

@Composable
fun toSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun getTextStyle(fontSize: Int, fontWeight: FontWeight, color: Color): TextStyle {
    return TextStyle(
        fontFamily = Pretendard,
        fontSize = toSp(fontSize.dp),
        color = color,
        fontWeight = fontWeight,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
}