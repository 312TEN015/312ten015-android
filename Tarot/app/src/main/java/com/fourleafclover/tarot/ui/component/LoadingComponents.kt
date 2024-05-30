package com.fourleafclover.tarot.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.backgroundColor_2
import com.fourleafclover.tarot.ui.theme.gray_6
import com.fourleafclover.tarot.ui.theme.white

@Composable
@Preview
fun LoadingCircle(
    modifier: Modifier = Modifier,
    loadingTitle: String = "상대방을 기다리는 중입니다...",
    loadingSubTitle: String = "1시간 안에 모두 입장하지 않으면 초대방이 사라져요!"
) {
    // 로딩 화면 회전
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember("rotate") { Animatable(currentRotation) }

    LaunchedEffect("rotate") {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
    }

    Column(
        modifier = Modifier
            .background(color = backgroundColor_2)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading),
            contentDescription = "",
            modifier = Modifier
                .wrapContentSize()
                .rotate(rotation.value)
        )
        TextH02M22(
            text = loadingTitle,
            color = white,
            modifier = Modifier.padding(bottom = 8.dp, top = 40.dp),
            textAlign = TextAlign.Center
        )
        TextB03M14(
            text = loadingSubTitle,
            color = gray_6,
            textAlign = TextAlign.Center
        )
    }
}