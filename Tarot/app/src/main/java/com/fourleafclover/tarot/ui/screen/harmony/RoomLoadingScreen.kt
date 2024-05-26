package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.sendRequest

// 추후 로딩 화면 컴포넌트화 하기
@Composable
@Preview
fun RoomLoadingScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current

    var send by remember { mutableStateOf(false) }

    PreventBackPressed()

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


    Column(modifier = Modifier
        .fillMaxSize()
        .background(gray_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.nonicons_loading_16),
            contentDescription ="",
            modifier = Modifier
                .wrapContentSize()
                .rotate(rotation.value)
        )
        TextH02M22(
            text = "{별명}님, 이제 궁합을 확인하러 가볼까요?",
            color = white,
            modifier = Modifier.padding(bottom = 8.dp, top = 40.dp),
            textAlign = TextAlign.Center)
        TextB03M14(
            text = "상대방을 초대하고 함께 실시간으로 궁합을 볼 수 있어요.",
            color = gray_5
        )
    }
}