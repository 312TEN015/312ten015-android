package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HarmonyViewModel: ViewModel() {

    private var userNickname = mutableStateOf("")
    private var partnerNickname = mutableStateOf("")

    var roomCode = mutableStateOf("")
    private var roomScenario = mutableStateOf(Scenario.Opening)

    fun setUserNickname(userNickname: String) {
        this.userNickname.value = userNickname
    }

    fun getUserNickname() = userNickname.value

    fun setPartnerNickname(partnerNickname: String) {
        this.partnerNickname.value = partnerNickname
    }

    fun getPartnerNickname() = partnerNickname.value

    fun setRoomScenario(newScenario: Scenario){
        this.roomScenario.value = newScenario
    }

    fun getRoomScenario() = roomScenario.value
}