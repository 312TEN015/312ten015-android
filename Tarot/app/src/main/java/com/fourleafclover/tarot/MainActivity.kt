package com.fourleafclover.tarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fourleafclover.tarot.ui.navigation.NavigationHost
import com.fourleafclover.tarot.ui.theme.TarotTheme

class MainActivity : ComponentActivity() {
    private lateinit var splashScreen: SplashScreen
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{
            viewModel.isLoading.value
        }

        setContent {
            TarotTheme {
                NavigationHost()
            }
        }
    }

    @Preview
    @Composable
    fun HomePreview(){
        TarotTheme {
            NavigationHost()
        }
    }


}



