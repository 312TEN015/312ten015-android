package com.fourleafclover.tarot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.ui.theme.TextButtonM16
import com.fourleafclover.tarot.ui.theme.gray_1
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.highlightPurple

@Preview
@Composable
fun ButtonNext(
    onClick: () -> Unit = {},
    enabled: () -> Boolean = { false },
    content:  @Composable () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .background(
                shape = RoundedCornerShape(10.dp),
                color = if (enabled()) highlightPurple else gray_6
            )
            .clickable { if (enabled()) { onClick() } }
            .wrapContentHeight()
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun ButtonText(isEnabled: Boolean, text: String = "선택완료"){
    TextButtonM16(
        text = text,
        modifier = Modifier.padding(vertical = 15.dp),
        color = if (isEnabled) gray_1 else gray_5
    )
}