package com.fourleafclover.tarot.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel: ViewModel() {
    private var _navController: NavHostController? = null
    val navController = _navController

    fun clear() = viewModelScope.launch { onCleared() }

    fun setNavController(navController: NavHostController) {
        _navController = navController
    }
}