package com.fourleafclover.tarot.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.component.BottomNavigationBar
import com.fourleafclover.tarot.ui.screen.main.HomeScreen
import com.fourleafclover.tarot.ui.screen.fortune.InputScreen
import com.fourleafclover.tarot.ui.screen.loading.LoadingScreen
import com.fourleafclover.tarot.ui.screen.my.MyTarotDetailScreen
import com.fourleafclover.tarot.ui.screen.my.MyTarotScreen
import com.fourleafclover.tarot.ui.screen.main.PagerOnBoarding
import com.fourleafclover.tarot.ui.screen.fortune.PickTarotScreen
import com.fourleafclover.tarot.ui.screen.fortune.ResultScreen
import com.fourleafclover.tarot.ui.screen.my.ShareDetailScreen
import com.fourleafclover.tarot.ui.screen.harmony.HarmonyResultScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomChatScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomCreateScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomGenderScreen
import com.fourleafclover.tarot.ui.screen.loading.RoomCreateLoadingScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomEnteringScreen
import com.fourleafclover.tarot.ui.screen.loading.RoomInviteLoadingScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomNicknameScreen
import com.fourleafclover.tarot.ui.screen.harmony.RoomShareScreen
import com.fourleafclover.tarot.ui.screen.my.MyTarotHarmonyDetail

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute == ScreenEnum.HomeScreen.name || currentRoute == ScreenEnum.MyTarotScreen.name)
            BottomNavigationBar(navController = navController)
    }
    ) { innerPadding -> innerPadding

        NavHost(navController = navController, startDestination = ScreenEnum.OnBoardingScreen.name) {
            composable(ScreenEnum.HomeScreen.name) {
                HomeScreen(navController)
            }
            composable(ScreenEnum.MyTarotScreen.name) {
                MyTarotScreen(navController)
            }
            composable(ScreenEnum.InputScreen.name) {
                InputScreen(navController)
            }
            composable(ScreenEnum.PickTarotScreen.name) {
                PickTarotScreen(navController)
            }
            composable(ScreenEnum.ResultScreen.name) {
                ResultScreen(navController)
            }
            composable(ScreenEnum.OnBoardingScreen.name) {
                if (MyApplication.prefs.isOnBoardingComplete()){
                    navigateInclusive(navController, ScreenEnum.HomeScreen.name)
                }else{
                    PagerOnBoarding(navController)
                }
            }
            composable(ScreenEnum.LoadingScreen.name) {
                LoadingScreen(navController)
            }
            composable(ScreenEnum.MyTarotDetailScreen.name) {
                MyTarotDetailScreen(navController)
            }
            composable(ScreenEnum.ShareDetailScreen.name) {
                ShareDetailScreen(navController)
            }
            composable(ScreenEnum.RoomCreateScreen.name) {
                RoomCreateScreen(navController)
            }
            composable(ScreenEnum.RoomGenderScreen.name) {
                RoomGenderScreen(navController)
            }
            composable(ScreenEnum.RoomNicknameScreen.name) {
                RoomNicknameScreen(navController)
            }
            composable(ScreenEnum.RoomCreateLoadingScreen.name) {
                RoomCreateLoadingScreen(navController)
            }
            composable(ScreenEnum.RoomShareScreen.name) {
                RoomShareScreen(navController)
            }
            composable(ScreenEnum.RoomInviteLoadingScreen.name) {
                RoomInviteLoadingScreen(navController)
            }
            composable(ScreenEnum.RoomEnteringScreen.name) {
                RoomEnteringScreen(navController)
            }
            composable(ScreenEnum.RoomChatScreen.name) {
                RoomChatScreen(navController)
            }
            composable(ScreenEnum.RoomResultScreen.name) {
                HarmonyResultScreen(navController)
            }
            composable(ScreenEnum.MyTarotHarmonyDetailScreen.name) {
                MyTarotHarmonyDetail(navController)
            }
        }

    }


}
