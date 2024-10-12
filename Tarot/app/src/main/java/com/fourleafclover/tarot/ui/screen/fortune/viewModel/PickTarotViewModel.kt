package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourleafclover.tarot.data.PickedCardNumberState
import com.fourleafclover.tarot.getRandomCards
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/** 카드 선택 할때 절차 관리 및 카드 관리 */
@HiltViewModel
class PickTarotViewModel: ViewModel() {
    // 선택한 카드들의 상태
    private var _pickedCardNumberState = mutableStateOf(PickedCardNumberState())
    val pickedCardNumberState get() = _pickedCardNumberState

    // 현재 선택한 카드
    private var _nowSelectedCardIdx = mutableStateOf(-1)
    val nowSelectedCardIdx get() = _nowSelectedCardIdx

    // 랜덤으로 섞인 카드 덱
    private var _cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
    val cards get() = _cards

    // 몇번째 카드 뽑는지
    private var _pickSequence = mutableStateOf(1)
    val pickSequence get() = _pickSequence

    // ---------------------------------------------------------------------------------------------

    fun clear() = viewModelScope.launch { onCleared() }

    fun initCardDeck(){
        _cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
        _nowSelectedCardIdx = mutableStateOf(-1)
        _pickedCardNumberState = mutableStateOf(PickedCardNumberState())
        _pickSequence = mutableStateOf(1)
    }

    fun setNowSelectedCardIdx(idx: Int) {
        _nowSelectedCardIdx.value = idx
    }

    fun resetNowSelectedCardIdx() {
        _nowSelectedCardIdx.value = -1
    }

    fun isCompleteButtonEnabled(): Boolean = _nowSelectedCardIdx.value != -1

    /** 몇 번째에 어떤 카드를 뽑았는지 저장 */
    fun setPickedCard(sequence: Int){
        val pickedCard = _cards[_nowSelectedCardIdx.value]
        when(sequence){
            1 -> _pickedCardNumberState.value.firstCardNumber = pickedCard
            2 -> _pickedCardNumberState.value.secondCardNumber = pickedCard
            3 -> _pickedCardNumberState.value.thirdCardNumber = pickedCard
        }
        _cards.remove(pickedCard)
    }

    fun getAlpha(isCardPicked: Boolean): Float {
        return if (isCardPicked) 1f else 0f
    }

    fun getDirectionText(sequence: Int): String {
        return when(sequence){
            1 -> "첫 번째 카드를 골라주세요."
            2 -> "두 번째 카드를 골라주세요."
            3 -> "세 번째 카드를 골라주세요."
            else -> ""
        }
    }
    
    fun getCardBlankText(sequence: Int): String {
        return when(sequence){
            1 -> "첫번째\n 카드"
            2 -> "두번째\n 카드"
            3 -> "세번째\n 카드"
            else -> ""
        }
    }

    /** sequence 번째 뽑은 카드 번호 반환 */
    fun getCardNumber(sequence: Int): Int {
        return when(sequence){
            1 -> _pickedCardNumberState.value.firstCardNumber
            2 -> _pickedCardNumberState.value.secondCardNumber
            3 -> _pickedCardNumberState.value.thirdCardNumber
            else -> 0
        }
    }

    fun getIsCardPicked(sequence: Int): Boolean {
        return when(sequence){
            1 -> _pickedCardNumberState.value.firstCardNumber != -1
            2 -> _pickedCardNumberState.value.secondCardNumber != -1
            3 -> _pickedCardNumberState.value.thirdCardNumber != -1
            else -> false
        }
    }
    
    fun moveToNextSequence() {
        _pickSequence.value += 1
    }
}