package com.fourleafclover.tarot.ui.navigation

import androidx.navigation.NavHostController

// 백 스택에 저장
fun navigateSaveState(navController: NavHostController, screenName: String){
    navController.navigate(screenName) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {  saveState = true }
        }
        launchSingleTop = true
        restoreState = true
    }
}


// 백 스택에 남기지 않음
fun navigateInclusive(navController: NavHostController, screenName: String){
    navController.navigate(screenName) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {  inclusive = true }
        }
        launchSingleTop = true
        restoreState = true
    }
}