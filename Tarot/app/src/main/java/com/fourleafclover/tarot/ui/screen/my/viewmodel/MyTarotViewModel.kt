package com.fourleafclover.tarot.ui.screen.my.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.TarotOutputDto
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MyTarotViewModel: ViewModel() {

    var _selectedTarotResult = mutableStateOf(TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null))
    val selectedTarotResult get() = _selectedTarotResult.value      // 선택한 결과 한개


    var _myTarotResults = mutableStateListOf<TarotOutputDto>()      // 모든 결과 리스트
    val myTarotResults get() = _myTarotResults


    private var isRoomOwnerTab = mutableStateOf(true) // 방장의 탭 터치했는지 여부

    var roomOwnerCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var roomOwnerCardNumbers : List<Int> = arrayListOf(0, 0, 0)
    var inviteeCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var inviteeCardNumbers : List<Int> = arrayListOf(0, 0, 0)


    fun selectItem(idx: Int){
        _selectedTarotResult.value = _myTarotResults[idx]
    }

    fun deleteSelectedItem(){
        val itemToDelete = _myTarotResults.find { it.tarotId == _selectedTarotResult.value.tarotId }
        _myTarotResults.remove(itemToDelete)
    }

    fun setMyTarotResults(results: ArrayList<TarotOutputDto>){
        _myTarotResults.clear()
        _myTarotResults.addAll(results)
        Log.d("", _myTarotResults.joinToString(" "))
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