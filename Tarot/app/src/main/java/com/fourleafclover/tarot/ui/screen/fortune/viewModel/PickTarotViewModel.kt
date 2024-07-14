package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.getRandomCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PickedCardNumberState(
    var firstCardNumber: Int = -1,
    var secondCardNumber: Int = -1,
    var thirdCardNumber: Int = -1
)

class PickTarotViewModel: ViewModel() {
    private var _pickedCardNumberState = MutableStateFlow(PickedCardNumberState())
    val pickedCardNumberState: StateFlow<PickedCardNumberState> = _pickedCardNumberState.asStateFlow()

    private var _nowSelectedCardIdx = mutableStateOf(-1)
    val nowSelectedCardIdx = _nowSelectedCardIdx

    private var _cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
    val cards = _cards

    fun initCardDeck(){
        _cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
        _nowSelectedCardIdx = mutableStateOf(-1)
        _pickedCardNumberState = MutableStateFlow(PickedCardNumberState())
    }

    fun setNowSelectedCardIdx(idx: Int) {
        _nowSelectedCardIdx.value = idx
    }

    fun resetNowSelectedCardIdx() {
        _nowSelectedCardIdx.value = -1
    }

    fun isCompleteButtonEnabled(): Boolean = _nowSelectedCardIdx.value != -1

    fun setPickedCard(sequence: Int){
        val pickedCard = _cards[_nowSelectedCardIdx.value]
        when(sequence){
            1 -> _pickedCardNumberState.value.firstCardNumber = pickedCard
            2 -> _pickedCardNumberState.value.secondCardNumber = pickedCard
            3 -> _pickedCardNumberState.value.thirdCardNumber = pickedCard
        }
        _cards.remove(pickedCard)
    }

    private fun firstCardPicked(): Boolean = _pickedCardNumberState.value.firstCardNumber != -1

    private fun secondCardPicked(): Boolean = _pickedCardNumberState.value.secondCardNumber != -1

    fun thirdCardPicked(): Boolean = _pickedCardNumberState.value.thirdCardNumber != -1

    fun getAlpha(isCardPicked: Boolean): Float {
        return if (isCardPicked) 1f else 0f
    }

    fun getDirectionText(context: Context, sequence: Int): String {
        return when(sequence){
            1 -> "첫 번째 카드를 골라주세요."
            2 -> "두 번째 카드를 골라주세요."
            3 -> "세 번째 카드를 골라주세요."
            else -> ""
        }
    }
    
    fun getCardBlankText(context: Context, sequence: Int): String {
        return when(sequence){
            1 -> "첫번째\n 카드"
            2 -> "두번째\n 카드"
            3 -> "세번째\n 카드"
            else -> ""
        }
    }
    
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
            1 -> firstCardPicked()
            2 -> secondCardPicked()
            3 -> thirdCardPicked()
            else -> false
        }
    }
}