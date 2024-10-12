package com.fourleafclover.tarot.ui.screen.my.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.TarotOutputDto
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ShareViewModel: ViewModel() {
    // 공유하기로 받은 타로 데이터
    private var _sharedTarotResult = mutableStateOf(TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null))
    val sharedTarotResult get() = _sharedTarotResult.value

    // 방장의 탭 터치했는지 여부
    private var isRoomOwnerTab = mutableStateOf(true)

    var roomOwnerCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var roomOwnerCardNumbers : List<Int> = arrayListOf(0, 0, 0)
    var inviteeCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var inviteeCardNumbers : List<Int> = arrayListOf(0, 0, 0)

    fun setSharedTarotResult(sharedTarotResult: TarotOutputDto) {
        this._sharedTarotResult.value = sharedTarotResult
    }

    fun distinguishCardResult(tarotResult: TarotOutputDto){
        roomOwnerCardResults = tarotResult.cardResults!!.slice(0..2)
        roomOwnerCardNumbers = tarotResult.cards.slice(0..2)
        inviteeCardResults = tarotResult.cardResults!!.slice(3..5)
        inviteeCardNumbers = tarotResult.cards.slice(3..5)
    }


    fun isRoomOwnerTab(): Boolean {
        return this.isRoomOwnerTab.value
    }

    fun roomOwnerTab() {
        this.isRoomOwnerTab.value = true
    }

    fun roomInviteeTab() {
        this.isRoomOwnerTab.value = false
    }

}