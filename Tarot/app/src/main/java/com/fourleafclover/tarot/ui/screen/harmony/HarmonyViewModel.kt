package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HarmonyViewModel: ViewModel() {

    private var userNickname = mutableStateOf("")
    var partnerNickname = mutableStateOf("")
    var roomCode = mutableStateOf("")

    fun setUserNickname(userNickname: String) {
        this.userNickname.value = userNickname
    }

    fun getUserNickname() = userNickname.value
}