package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.loadingViewModel
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.navigation.navigateSaveState

class LoadingViewModel: ViewModel() {
    private var isLoading = mutableStateOf(false)
    private var destination = ScreenEnum.HomeScreen

    fun updateLoadingState(isLoading: Boolean){
        this.isLoading.value = isLoading
    }

    fun getIsLoading() = isLoading.value

    fun startLoading(navController: NavHostController, loadingScreenEnum: ScreenEnum, destination: ScreenEnum){
        updateLoadingState(true)
        navigateSaveState(navController, loadingScreenEnum.name)
        this.destination = destination
    }

    fun endLoading(navController: NavHostController){
        updateLoadingState(false)
        navigateInclusive(navController, destination.name)
    }
}