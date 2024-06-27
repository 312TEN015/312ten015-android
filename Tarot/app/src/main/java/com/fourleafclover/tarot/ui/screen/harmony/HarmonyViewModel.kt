package com.fourleafclover.tarot.ui.screen.harmony

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HarmonyViewModel: ViewModel() {

    private var userNickname = mutableStateOf("")   // 사용자 닉네임
    private var partnerNickname = mutableStateOf("")    // 상대방 닉네임

    var roomCode = mutableStateOf("")       // 생성된 방 코드
    private var roomCreatedAt = "" // 방 생성된 시간

    var dynamicLink = ""
    var shortLink = ""

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

    fun setRoomCreatedAt() {
        // TODO 방 생성 시간 저장하기
        roomCreatedAt = ""
    }
    
    fun getRoomCreatedAt() {
        // TODO 1시간 지났는지 검사 후 지났으면 초기화 후 리턴시키기
    }
}