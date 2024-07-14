package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.fortuneViewModel
import com.fourleafclover.tarot.getRandomCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PickCardState(
    var firstCardNumber: Int = -1,
    var secondCardNumber: Int = -1,
    var thirdCardNumber: Int = -1
)

class PickTarotViewModel: ViewModel() {
    private val _pickCardState = MutableStateFlow(PickCardState())
    val pickCardState: StateFlow<PickCardState> = _pickCardState.asStateFlow()

    private var _nowSelectedCardIdx = mutableStateOf(-1)
    val nowSelectedCardIdx = _nowSelectedCardIdx

    private val _cards = mutableStateListOf<Int>().apply { addAll(getRandomCards()) }
    val cards = _cards

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
            1 -> _pickCardState.value.firstCardNumber = pickedCard
            2 -> _pickCardState.value.secondCardNumber = pickedCard
            3 -> _pickCardState.value.thirdCardNumber = pickedCard
        }
        fortuneViewModel.addPickedCard(pickedCard)
        _cards.remove(pickedCard)
    }

    private fun firstCardPicked(): Boolean = _pickCardState.value.firstCardNumber != -1

    private fun secondCardPicked(): Boolean = _pickCardState.value.secondCardNumber != -1

    fun thirdCardPicked(): Boolean = _pickCardState.value.thirdCardNumber != -1

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
            1 -> _pickCardState.value.firstCardNumber
            2 -> _pickCardState.value.secondCardNumber
            3 -> _pickCardState.value.thirdCardNumber
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