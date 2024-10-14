package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HarmonyViewModel @Inject constructor(): ViewModel() {

    private var userNickname = mutableStateOf("")   // 사용자 닉네임
    private var partnerNickname = mutableStateOf("")    // 상대방 닉네임

    private var _roomId = mutableStateOf("")       // 현재 들어가 있는 방 코드
    val roomId get() = _roomId

    private var _createdRoomId = mutableStateOf("")       // 내가 생성한 방 코드
    val createdRoomId get() = _createdRoomId

    private var _invitedRoomId = mutableStateOf("")       // 초대된 방 코드
    val invitedRoomId get() = _invitedRoomId


    private var _isRoomOwner = mutableStateOf(false)
    val isRoomOwner get() = _isRoomOwner

    var dynamicLink = ""
    var shortLink = ""

    fun clear() {
        userNickname.value = ""
        partnerNickname.value = ""
        _roomId.value = ""
        _createdRoomId.value = ""
        _invitedRoomId.value = ""
        _isRoomOwner.value = false
        dynamicLink = ""
        shortLink = ""
    }

    fun setIsRoomOwner(isRoomOwner: Boolean) {
        _isRoomOwner.value = isRoomOwner
    }

    fun getOwnerNickname(): String {
        return if (isRoomOwner.value) userNickname.value else partnerNickname.value
    }

    fun getInviteeNickname(): String {
        return if (!isRoomOwner.value) userNickname.value else partnerNickname.value
    }

    fun setUserNickname(userNickname: String) {
        this.userNickname.value = userNickname
    }

    fun getUserNickname() = userNickname.value

    fun setPartnerNickname(partnerNickname: String) {
        this.partnerNickname.value = partnerNickname
    }

    fun getPartnerNickname() = partnerNickname.value

    // 기존 내가 만든 방에 입장하기
    fun enterExistingRoom() {
        _roomId.value = MyApplication.prefs.getHarmonyRoomId()
        _createdRoomId.value = MyApplication.prefs.getHarmonyRoomId()
    }

    // 새로운 방을 생성
    fun createNewRoom(newRoomId: String) {
        _roomId.value = newRoomId
        _createdRoomId.value = newRoomId
        _isRoomOwner.value = true

        val mNow = System.currentTimeMillis()
        val mDate = Date(mNow)
        MyApplication.prefs.saveHarmonyRoomCreatedAt(mDate.toString())
        MyApplication.prefs.saveHarmonyRoomId(newRoomId)
    }

    // 초대 또는 생성한 방에서 나가기, 1시간 지나서 사라짐, 궁합 보기 완료
    fun deleteRoom() {
        _roomId.value = ""

        if (_isRoomOwner.value) {
            _createdRoomId.value = ""
            MyApplication.prefs.saveHarmonyRoomId("")
            MyApplication.prefs.saveHarmonyRoomCreatedAt("")
        } else {
            _invitedRoomId.value = ""
        }

        _isRoomOwner.value = false
//        pickTarotViewModel.clear()
//        resultViewModel.clear()
//        chatViewModel.clear()
//        harmonyShareViewModel.clear()

    }


    // 초대된 방에 들어가기
    fun enterInvitedRoom(invitedRoomId: String) {
        _roomId.value = invitedRoomId
        _invitedRoomId.value = invitedRoomId
    }

}