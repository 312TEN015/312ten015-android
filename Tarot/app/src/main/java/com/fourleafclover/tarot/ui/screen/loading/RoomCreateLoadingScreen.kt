package com.fourleafclover.tarot.ui.screen.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.ui.component.LoadingCircle
import com.fourleafclover.tarot.ui.component.getBackgroundModifier
import com.fourleafclover.tarot.ui.navigation.PreventBackPressed
import com.fourleafclover.tarot.ui.screen.harmony.emitCreate
import com.fourleafclover.tarot.ui.screen.harmony.setOnCreateComplete
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.HarmonyShareViewModel
import com.fourleafclover.tarot.ui.screen.harmony.viewmodel.LoadingViewModel
import com.fourleafclover.tarot.ui.theme.gray_9

// 추후 로딩 화면 컴포넌트화 하기
@Composable
@Preview
fun RoomCreateLoadingScreen(
    navController: NavHostController = rememberNavController(),
    harmonyShareViewModel: HarmonyShareViewModel = hiltViewModel(),
    loadingViewModel: LoadingViewModel = hiltViewModel()
){

    if (!loadingViewModel.isLoading.value) {
        loadingViewModel.endLoading(navController)
    }

    PreventBackPressed()

    LaunchedEffect(Unit){
        // 새로운 방 생성
        setOnCreateComplete(harmonyShareViewModel, loadingViewModel)
        emitCreate()
    }

    Column(modifier = getBackgroundModifier(color = gray_9),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadingCircle(
            loadingTitle = "${harmonyShareViewModel.getUserNickname()}님, 이제 궁합을\n확인하러 가볼까요?",
            loadingSubTitle = "상대방을 초대하고 함께\n실시간으로 궁합을 볼 수 있어요."
        )
    }
}