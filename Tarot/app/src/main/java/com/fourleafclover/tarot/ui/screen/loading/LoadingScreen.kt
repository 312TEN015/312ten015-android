package com.fourleafclover.tarot.ui.screen.loading

import android.os.Handler
import android.os.Looper
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
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.screen.harmony.onResult
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.getTextStyle
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.sendRequest


@Composable
@Preview
fun LoadingScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current

    if (!loadingViewModel.getIsLoading()) {
        loadingViewModel.endLoading(navController)
    }

    var send by remember { mutableStateOf(false) }

    PreventBackPressed()


    // 요청을 한번만 보내도록 함
    if (!send){
        sendRequest(localContext, navController)
        send = true

        /* 테스트 코드 */
        Handler(Looper.getMainLooper())
            .postDelayed({
                onResult()
            }, 4000)
    }

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
        TextH02M22(text = "선택하신 카드의 의미를\n열심히 해석하고 있어요!",
            color = white,
            modifier = Modifier.padding(bottom = 16.dp, top = 40.dp),
            textAlign = TextAlign.Center)
        TextB03M14(text = "잠시만 기다려주세요",
            color = gray_5
        )
    }
}