package com.fourleafclover.tarot.ui.navigation

import androidx.navigation.NavHostController

// 백 스택에 저장
fun navigateSaveState(navController: NavHostController, screenName: String){
    navController.navigate(screenName) {
        popUpTo(screenName) {  inclusive = false }
    }
}


// 백 스택에 남기지 않음
fun navigateInclusive(navController: NavHostController, screenName: String){
    navController.navigate(screenName) {
        popUpTo(screenName) {  inclusive = true }
    }
}