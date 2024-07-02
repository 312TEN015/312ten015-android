package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class NicknameViewModel: ViewModel() {

    private var nickname = mutableStateOf("사용자")
    private val maxChar = 10
    private var isCaptionVisible = mutableStateOf(false)


    fun updateNickname(newText: String): Boolean{
        if (newText.length > maxChar){
            isCaptionVisible.value = true
            return false
        }

        isCaptionVisible.value = false
        nickname.value = newText
        return true
    }

    fun isCompleted(): Boolean {
        return nickname.value.isNotEmpty()
    }

    fun getNicknameLength() = nickname.value.length

    fun getNicknameString() = nickname.value

    fun getIsCaptionVisible() = isCaptionVisible.value

}