package com.fourleafclover.tarot.ui.screen.harmony

import android.os.Handler
import android.os.Looper
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.component.LoadingCircle
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.theme.TextB03M14
import com.fourleafclover.tarot.ui.theme.TextH02M22
import com.fourleafclover.tarot.ui.theme.gray_5
import com.fourleafclover.tarot.ui.theme.gray_9
import com.fourleafclover.tarot.ui.theme.white
import org.json.JSONObject

// 추후 로딩 화면 컴포넌트화 하기
@Composable
@Preview
fun RoomCreateLoadingScreen(navController: NavHostController = rememberNavController()) {

    if (!loadingViewModel.getIsLoading()) {
        loadingViewModel.endLoading(navController)
    }

    PreventBackPressed()

    var initialize by remember { mutableStateOf(false) }

    /* 한번만 실행 */
    if (!initialize) {
        initialize = true

        // 새로운 방 생성
        MyApplication.socket.connect()
        if (harmonyViewModel.roomCode.value.isEmpty()) {
            MyApplication.socket.emit("create")
            MyApplication.socket.on("createComplete", onCreate)
        }

        Handler(Looper.getMainLooper())
            .postDelayed({
                loadingViewModel.updateLoadingState(false)
            }, 4000)
    }

    Column(modifier = getBackgroundModifier(color = gray_9),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingCircle(
            loadingTitle = "${harmonyViewModel.getUserNickname()}님, 이제 궁합을\n확인하러 가볼까요?",
            loadingSubTitle = "상대방을 초대하고 함께\n실시간으로 궁합을 볼 수 있어요."
        )
    }
}