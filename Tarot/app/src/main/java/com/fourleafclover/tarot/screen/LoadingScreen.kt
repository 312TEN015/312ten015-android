package com.fourleafclover.tarot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_8

@Composable
@Preview
fun LoadingScreen(){

    Column(modifier = Modifier.fillMaxSize().background(gray_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.nonicons_loading_16),
            contentDescription ="",
            modifier = Modifier.wrapContentSize()
        )
        Text(text = "선택하신 카드의 의미\n열심히 해석하고 있어요!", style = getTextStyle(
            fontSize = 22,
            fontWeight = FontWeight.Medium
        ),
            modifier = Modifier.padding(bottom = 16.dp, top = 40.dp))
        Text(text = "잠시만 기다려주세요",
            style = getTextStyle(
            fontSize = 14,
            fontWeight = FontWeight.Medium,
            color = gray_5
        ))
    }
}