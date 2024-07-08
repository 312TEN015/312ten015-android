package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.harmonyViewModel
import java.util.Date

class RoomCreateViewModel: ViewModel() {

    var openRoomDeletedDialog = mutableStateOf(false)
    var openRoomExistDialog = mutableStateOf(false)
    var createNewRoom = mutableStateOf(false)


    fun checkRoomExist() {

        val roomId = MyApplication.prefs.getHarmonyRoomId()
        val createdAtString = MyApplication.prefs.getHarmonyRoomCreatedAt()

        if (roomId.isNotEmpty() && createdAtString.isNotEmpty()) {
            val createdAt = Date(MyApplication.prefs.getHarmonyRoomCreatedAt())
            val mNow = System.currentTimeMillis()
            val diffMilliseconds = Date(mNow).time - createdAt.time
            val diffHours = diffMilliseconds / (60 * 60 * 1000)
            if (diffHours >= 1) {
                // 1시간 지나서 사라짐
                openRoomDeletedDialog.value = true
                MyApplication.prefs.saveHarmonyRoomId("")
                MyApplication.prefs.saveHarmonyRoomCreatedAt("")
            } else {
                // 이미 생성하신 초대방이 있어요
                openRoomExistDialog.value = true
                harmonyViewModel.roomId.value = roomId
            }
        } else {
            // 새로 초대방 만들기
            createNewRoom.value = true
        }
    }

}