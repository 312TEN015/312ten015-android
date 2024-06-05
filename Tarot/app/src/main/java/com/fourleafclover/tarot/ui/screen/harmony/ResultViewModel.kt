package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.harmonyViewModel

class ResultViewModel() : ViewModel() {

    private var openCloseDialog = mutableStateOf(false) // close dialog state
    private var openCompleteDialog = mutableStateOf(false)  // 타로 저장 완료 dialog state
    private var saveState = mutableStateOf(false)   // 타로 결과 저장했는지 여부
    private var isMyTab = mutableStateOf(true) // 나의 탭 터치했는지 여부

    fun openCloseDialog(){
        this.openCloseDialog.value = true
    }

    fun closeCloseDialog(){
        this.openCloseDialog.value = false
    }

    fun isCloseDialogOpen(): Boolean {
        return this.openCloseDialog.value
    }


    fun openCompleteDialog(){
        this.openCompleteDialog.value = true
    }

    fun closeCompleteDialog(){
        this.openCompleteDialog.value = false
    }

    fun isCompleteDialogOpen(): Boolean {
        return this.openCompleteDialog.value
    }

    fun saveResult(){
        this.saveState.value = true
    }

    fun isSaved(): Boolean{
        return this.saveState.value
    }

    fun isMyTab(): Boolean {
        return this.isMyTab.value
    }

    fun myTab(){
        this.isMyTab.value = true
    }

    fun partnerTab(){
        this.isMyTab.value = false
    }

    fun getNickname(): String {
        return if (isMyTab()) harmonyViewModel.getUserNickname() else harmonyViewModel.getPartnerNickname()
    }

}