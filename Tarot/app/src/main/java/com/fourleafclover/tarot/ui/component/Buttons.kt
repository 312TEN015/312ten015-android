package com.fourleafclover.tarot.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
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
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = highlightPurple,
            contentColor = gray_1,
            disabledContainerColor = gray_6,
            disabledContentColor = gray_5
        ),
        enabled = enabled(),
        content = { content() }
    )
}

@Composable
fun ButtonText(isEnabled: Boolean, text: String = "선택완료"){
    TextButtonM16(
        text = text,
        modifier = Modifier.padding(vertical = 15.dp),
        color = if (isEnabled) gray_1 else gray_5
    )
}