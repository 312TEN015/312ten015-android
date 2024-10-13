package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(): ViewModel() {

    private var _nickname = mutableStateOf("")
    val nickname get() = _nickname

    private val maxChar = 10

    private var _isCaptionVisible = mutableStateOf(false)
    val isCaptionVisible get() = _isCaptionVisible

    fun clear() {
        _nickname = mutableStateOf("")
        _isCaptionVisible = mutableStateOf(false)
    }


    fun updateNickname(newText: String): Boolean{
        if (newText.length > maxChar){
            _isCaptionVisible.value = true
            return false
        }

        _isCaptionVisible.value = false
        _nickname.value = newText
        return true
    }

    fun isCompleted(): Boolean {
        return _nickname.value.isNotEmpty()
    }

    fun getNicknameLength() = _nickname.value.length

}