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
            .padding(bottom = 34.dp),
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
fun ContentComplete(boolean: Boolean){
    TextButtonM16(
        text = "선택완료",
        modifier = Modifier.padding(vertical = 8.dp),
        color = if (boolean) gray_1 else gray_5
    )
}

@Composable
fun ContentNext(boolean: Boolean){
    TextButtonM16(
        text = "다음",
        modifier = Modifier.padding(vertical = 8.dp),
        color = if (boolean) gray_1 else gray_5
    )
}