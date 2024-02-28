package com.fourleafclover.tarot.ui.theme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.sp
import com.fourleafclover.tarot.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_semibold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal)
)

@Composable
fun toSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun getTextStyle(fontSize: Int, fontWeight: FontWeight, color: Color = white): TextStyle {
    return TextStyle(
        fontFamily = Pretendard,
        fontSize = toSp(fontSize.dp),
        color = color,
        fontWeight = fontWeight,
        platformStyle = PlatformTextStyle(
            includeFontPadding = true
        )
    )
}

@Composable
fun TextH01M26(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 34.sp,
        style = getTextStyle(fontSize = 26, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextH02M22(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 30.sp,
        style = getTextStyle(fontSize = 22, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextH03SB18(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 24.sp,
        style = getTextStyle(fontSize = 18, fontWeight = FontWeight.SemiBold, color = color)
    )
}

@Composable
fun TextB01M18(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 28.sp,
        style = getTextStyle(fontSize = 18, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextB02M16(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 28.sp,
        style = getTextStyle(fontSize = 16, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextB03M14(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 20.sp,
        style = getTextStyle(fontSize = 14, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextB04M12(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 16.sp,
        style = getTextStyle(fontSize = 12, fontWeight = FontWeight.Medium, color = color)
    )
}

@Composable
fun TextCaptionM12(text: String,
               modifier: Modifier = Modifier,
               color: Color = gray_9, ){
    return Text(
        text = text,
        modifier = modifier,
        lineHeight = 20.sp,
        style = getTextStyle(fontSize = 12, fontWeight = FontWeight.Normal, color = color)
    )
}