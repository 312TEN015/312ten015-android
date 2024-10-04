package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourleafclover.tarot.data.CardResultData
import com.fourleafclover.tarot.data.TarotInputDto
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.harmonyShareViewModel
import com.fourleafclover.tarot.pickTarotViewModel
import com.fourleafclover.tarot.questionInputViewModel
import kotlinx.coroutines.launch

/** 타로 뽑기 결과 관리 */
/** 타로 뽑기 결과 & 궁합보기 결과 화면에서 사용 */
class ResultViewModel() : ViewModel() {

    // 타로보기 -------------------------------------------------------------------------------------

    private var _tarotResult = mutableStateOf(TarotOutputDto())
    val tarotResult get() = _tarotResult

    // 궁합보기 -------------------------------------------------------------------------------------

    var myCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var myCardNumbers : List<Int> = arrayListOf(0, 0, 0)
    var partnerCardResults : List<CardResultData> = arrayListOf(CardResultData(), CardResultData(), CardResultData())
    var partnerCardNumbers : List<Int> = arrayListOf(0, 0, 0)

    private var isMyTab = mutableStateOf(true) // 나의 탭 터치했는지 여부

    var isMatchResultPrepared = mutableStateOf(false)

    // 공통 ----------------------------------------------------------------------------------------

    private var _openCloseDialog = mutableStateOf(false) // close dialog state
    val openCloseDialog get() = _openCloseDialog

    private var _openCompleteDialog = mutableStateOf(false)  // 타로 저장 완료 dialog state
    val openCompleteDialog get() = _openCompleteDialog

    private var _saveState = mutableStateOf(false)   // 타로 결과 저장했는지 여부
    val saveState get() = _saveState

    // ---------------------------------------------------------------------------------------------

    /** 타로 보기 결과 요청 */
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

    fun clear() = viewModelScope.launch { onCleared() }

    fun setIsMatchResultPrepared(isPrepared: Boolean) {
        isMatchResultPrepared.value = isPrepared
    }

    fun initResult(){
        _tarotResult = mutableStateOf(TarotOutputDto())

        myCardResults = arrayListOf(CardResultData(), CardResultData(), CardResultData())
        myCardNumbers = arrayListOf(0, 0, 0)
        partnerCardResults = arrayListOf(CardResultData(), CardResultData(), CardResultData())
        partnerCardNumbers = arrayListOf(0, 0, 0)

        isMyTab = mutableStateOf(true)

        _openCloseDialog.value = false
        _openCompleteDialog.value = false
        _saveState.value = false
    }

    fun distinguishCardResult(tarotResult: TarotOutputDto){
        initResult()
        setTarotResult(tarotResult)
        if (harmonyShareViewModel.isRoomOwner.value) {
            myCardResults = tarotResult.cardResults!!.slice(0..2)
            myCardNumbers = tarotResult.cards.slice(0..2)
            partnerCardResults = tarotResult.cardResults!!.slice(3..5)
            partnerCardNumbers = tarotResult.cards.slice(3..5)
        } else {
            partnerCardResults = tarotResult.cardResults!!.slice(0..2)
            partnerCardNumbers = tarotResult.cards.slice(0..2)
            myCardResults = tarotResult.cardResults!!.slice(3..5)
            myCardNumbers = tarotResult.cards.slice(3..5)
        }
    }

    fun openCloseDialog(){
        _openCloseDialog.value = true
    }

    fun closeCloseDialog(){
        _openCloseDialog.value = false
    }

    fun openCompleteDialog(){
        _openCompleteDialog.value = true
    }

    fun closeCompleteDialog(){
        _openCompleteDialog.value = false
    }

    fun saveResult(){
        _saveState.value = true
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

    fun setTarotResult(result: TarotOutputDto) {
        _tarotResult.value = result
    }

}