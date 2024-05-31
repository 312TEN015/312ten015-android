package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HarmonyViewModel: ViewModel() {

    private var userNickname = mutableStateOf("")   // 사용자 닉네임
    var partnerNickname = mutableStateOf("")    // 상대방 닉네임
    var roomCode = mutableStateOf("")       // 생성된 방 코드

    fun setUserNickname(userNickname: String) {
        this.userNickname.value = userNickname
    }

    fun getUserNickname() = userNickname.value
    
    fun getPartnerNickname() = partnerNickname.value
}