package com.fourleafclover.tarot.ui.screen.loading

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.component.LoadingCircle
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.screen.harmony.onCreateComplete
import com.fourleafclover.tarot.ui.theme.gray_9

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
        if (harmonyViewModel.roomId.value.isEmpty()) {
            MyApplication.socket.emit("create")
            Log.d("socket-test", "emit create")

            MyApplication.socket.on("onCreateComplete", onCreateComplete)
        }

        /* 테스트 코드 */
        Handler(Looper.getMainLooper())
            .postDelayed({
                         onCreateComplete()
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