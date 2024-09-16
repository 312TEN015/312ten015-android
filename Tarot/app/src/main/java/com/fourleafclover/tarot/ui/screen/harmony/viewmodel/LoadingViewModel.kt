package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.fourleafclover.tarot.ui.navigation.ScreenEnum
import com.fourleafclover.tarot.ui.navigation.navigateInclusive
import com.fourleafclover.tarot.ui.navigation.navigateSaveState

/** 로딩 상태를 관리 */
class LoadingViewModel: ViewModel() {
    private var _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading

    private var _destination = ScreenEnum.HomeScreen
    val destination get() = _destination


    /** 로딩 실행 여부를 업데이트
     * changeDestination()과  함께 사용
     * false: 로딩 끝내기, true: 로딩 시작 */
    fun updateLoadingState(isLoading: Boolean){
        _isLoading.value = isLoading
    }

    /** 로딩 화면을 시작 */
    fun startLoading(navController: NavHostController, loadingScreenEnum: ScreenEnum, destination: ScreenEnum){
        updateLoadingState(true)
        navigateSaveState(navController, loadingScreenEnum.name)
        _destination = destination
    }

    /** 로딩 화면을 끝냄 */
    fun endLoading(navController: NavHostController){
        updateLoadingState(false)
        navigateInclusive(navController, _destination.name)
    }

    /** 로딩 후 도착 화면 수정 */
    fun changeDestination(destination: ScreenEnum){
        _destination = destination
    }

}