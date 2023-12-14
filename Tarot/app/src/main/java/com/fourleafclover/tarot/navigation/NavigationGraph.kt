package com.fourleafclover.tarot.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fourleafclover.tarot.BottomNavigationBar
import com.fourleafclover.tarot.screen.HomeScreen
import com.fourleafclover.tarot.screen.InputScreen
import com.fourleafclover.tarot.screen.MyTarotScreen
import com.fourleafclover.tarot.screen.PickTarotScreen
import com.fourleafclover.tarot.screen.ResultScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }
    ) { innerPadding -> innerPadding

        NavHost(navController = navController, startDestination = ScreenEnum.HomeScreen.name) {
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
        }

    }


}
