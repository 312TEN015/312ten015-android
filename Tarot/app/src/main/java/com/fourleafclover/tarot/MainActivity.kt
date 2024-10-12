package com.fourleafclover.tarot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fourleafclover.tarot.ui.navigation.NavigationHost
import com.fourleafclover.tarot.ui.theme.TarotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var splashScreen: SplashScreen
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{
            splashViewModel.isLoading.value
        }

        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data

        if (appLinkData != null){
            Log.d("", appLinkAction.toString())
            Log.d("", appLinkData.toString())
            Log.d("", appLinkData.getQueryParameter("resultId").toString())
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



