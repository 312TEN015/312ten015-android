package com.fourleafclover.tarot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fourleafclover.tarot.navigation.NavigationHost
import com.fourleafclover.tarot.ui.theme.TarotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomePreview()
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



