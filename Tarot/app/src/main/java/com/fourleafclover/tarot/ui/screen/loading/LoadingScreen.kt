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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.component.LoadingCircle
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.screen.harmony.onResult
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_8
import com.fourleafclover.tarot.ui.theme.white
import com.fourleafclover.tarot.utils.getMatchResult
import com.fourleafclover.tarot.utils.sendRequest


@Composable
@Preview
fun LoadingScreen(navController: NavHostController = rememberNavController()){
    val localContext = LocalContext.current

    if (!loadingViewModel.getIsLoading()) {
        loadingViewModel.endLoading(navController)
    }

    PreventBackPressed()

    LaunchedEffect(Unit){
        if (loadingViewModel.getDestination() == ScreenEnum.ResultScreen){
            sendRequest(localContext, navController)
        }
        else if (loadingViewModel.getDestination() == ScreenEnum.RoomResultScreen){
            /* 테스트 코드 */
//            Handler(Looper.getMainLooper())
//                .postDelayed({
//                    onResult(localContext, navController)
//                }, 4000)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(gray_8),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingCircle(
            loadingTitle = "선택하신 카드의 의미를\n열심히 해석하고 있어요!",
            loadingSubTitle = "잠시만 기다려주세요"
        )
    }
}