package com.fourleafclover.tarot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.ui.theme.gray_7
import com.fourleafclover.tarot.ui.theme.transparent

fun getOutlinedRectangleModifier(borderColor: Color, fillColor: Color, cornerRadius: Dp, borderWidth: Dp = 1.dp): Modifier {
    return Modifier
        .background(color = fillColor)
        .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(cornerRadius))
}