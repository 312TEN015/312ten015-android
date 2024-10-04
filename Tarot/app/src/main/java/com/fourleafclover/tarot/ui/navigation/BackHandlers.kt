package com.fourleafclover.tarot.ui.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.dialogViewModel
import com.fourleafclover.tarot.ui.component.OpenCloseChatDialog

@Composable
fun FinishOnBackPressed() {
    val context = LocalContext.current
    var backPressedState by remember { mutableStateOf(true) }
    var backPressedTime = 0L

    BackHandler(enabled = backPressedState) {
        if(System.currentTimeMillis() - backPressedTime <= 1000L) {
            // 앱 종료
            (context as Activity).finish()
        } else {
            backPressedState = true
            MyApplication.toastUtil.makeShortToast("한 번 더 누르시면 앱이 종료됩니다.")
        }
        backPressedTime = System.currentTimeMillis()
    }
}

@Composable
fun PreventBackPressed(){
    BackHandler { }
}

@Composable
fun NavigateHomeOnBackPressed(navController: NavHostController) {
    BackHandler {
        navigateInclusive(navController, ScreenEnum.HomeScreen.name)
    }
}

@Composable
fun OpenDialogOnBackPressed() {
    BackHandler {
        if (dialogViewModel.openDialog) dialogViewModel.closeDialog()
        else dialogViewModel.openDialog()
    }
}


