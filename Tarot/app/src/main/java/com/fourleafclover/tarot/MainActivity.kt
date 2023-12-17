package com.fourleafclover.tarot

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.ConditionVariable
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fourleafclover.tarot.navigation.NavigationHost
import com.fourleafclover.tarot.ui.theme.TarotTheme
import java.util.concurrent.locks.Condition

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



