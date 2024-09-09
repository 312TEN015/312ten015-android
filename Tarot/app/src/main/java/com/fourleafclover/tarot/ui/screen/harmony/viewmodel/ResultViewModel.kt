package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.chatViewModel
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.TarotInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.harmonyViewModel
import com.fourleafclover.tarot.pickTarotViewModel
import com.fourleafclover.tarot.questionInputViewModel

/** 타로 뽑기 결과 관리 */
class ResultViewModel() : ViewModel() {

    private var openCloseDialog = mutableStateOf(false) // close dialog state
    private var openCompleteDialog = mutableStateOf(false)  // 타로 저장 완료 dialog state
    private var saveState = mutableStateOf(false)   // 타로 결과 저장했는지 여부
    private var isMyTab = mutableStateOf(true) // 나의 탭 터치했는지 여부

    var myCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var myCardNumbers : List<Int> = arrayListOf(0, 0, 0)
    var partnerCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var partnerCardNumbers : List<Int> = arrayListOf(0, 0, 0)

    private var _tarotResult = mutableStateOf(TarotOutputDto())
    val tarotResult = _tarotResult

    fun initResult(){
        openCloseDialog.value = false
        openCompleteDialog.value = false
        saveState.value = false
    }

    fun distinguishCardResult(tarotResult: TarotOutputDto){
        if (tarotResult.cards[0] == chatViewModel.chatState.value.pickedCardNumberState.firstCardNumber){
            myCardResults = tarotResult.cardResults!!.slice(0..2)
            myCardNumbers = tarotResult.cards.slice(0..2)
            partnerCardResults = tarotResult.cardResults!!.slice(3..5)
            partnerCardNumbers = tarotResult.cards.slice(3..5)
        } else {
            myCardResults = tarotResult.cardResults!!.slice(3..5)
            myCardNumbers = tarotResult.cards.slice(3..5)
            partnerCardResults = tarotResult.cardResults!!.slice(0..2)
            partnerCardNumbers = tarotResult.cards.slice(0..2)
        }
    }

    fun tmpDistinguishCardResult(tarotResult: TarotOutputDto){
        initResult()
        myCardResults = tarotResult.cardResults!!.slice(0..2)
        myCardNumbers = tarotResult.cards.slice(0..2)
        partnerCardResults = tarotResult.cardResults!!.slice(3..5)
        partnerCardNumbers = tarotResult.cards.slice(3..5)
    }

    fun openCloseDialog(){
        this.openCloseDialog.value = true
    }

    fun closeCloseDialog(){
        this.openCloseDialog.value = false
    }

    fun isCloseDialogOpen(): Boolean {
        return this.openCloseDialog.value
    }


    fun openCompleteDialog(){
        this.openCompleteDialog.value = true
    }

    fun closeCompleteDialog(){
        this.openCompleteDialog.value = false
    }

    fun isCompleteDialogOpen(): Boolean {
        return this.openCompleteDialog.value
    }

    fun saveResult(){
        this.saveState.value = true
    }

    fun isSaved(): Boolean{
        return this.saveState.value
    }

    fun isMyTab(): Boolean {
        return this.isMyTab.value
    }

    fun myTab(){
        this.isMyTab.value = true
    }

    fun partnerTab(){
        this.isMyTab.value = false
    }

    fun getNickname(): String {
        return if (isMyTab()) harmonyViewModel.getUserNickname() else harmonyViewModel.getPartnerNickname()
    }

    fun setTarotResult(result: TarotOutputDto) {
        _tarotResult.value = result
    }

    fun getTarotInputDto() = TarotInputDto(
        questionInputViewModel.answer1.value.text,
        questionInputViewModel.answer2.value.text,
        questionInputViewModel.answer3.value.text,
        arrayListOf(
            pickTarotViewModel.pickedCardNumberState.value.firstCardNumber,
            pickTarotViewModel.pickedCardNumberState.value.secondCardNumber,
            pickTarotViewModel.pickedCardNumberState.value.thirdCardNumber,
        )
    )

}