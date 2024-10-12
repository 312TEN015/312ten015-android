package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourleafclover.tarot.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** 유저가 입력한 답변 관리 */
@HiltViewModel
class QuestionInputViewModel @Inject constructor(): ViewModel() {
    private var _answer1 = mutableStateOf(TextFieldValue(""))
    val answer1 get() = _answer1

    private var _answer2 = mutableStateOf(TextFieldValue(""))
    val answer2 get() = _answer2

    private var _answer3 = mutableStateOf(TextFieldValue(""))
    val answer3 get() = _answer3

    private var _maxChar = 50
    val maxChar get() = _maxChar


    fun clear() = viewModelScope.launch { onCleared() }

    fun initAnswers(){
        // 인풋 초기화
        _answer1.value = TextFieldValue("")
        _answer2.value = TextFieldValue("")
        _answer3.value = TextFieldValue("")
    }

    fun allFilled(): Boolean = _answer1.value.text.isNotBlank() && _answer2.value.text.isNotBlank() && _answer3.value.text.isNotBlank()

    fun getNowTextField(idx: Int): MutableState<TextFieldValue> {
        return when (idx) {
            1 -> _answer1
            2 -> _answer2
            3 -> _answer3
            else -> { _answer1 }
        }
    }

    fun setTextField(idx: Int, newText: TextFieldValue){
        getNowTextField(idx).value = newText
    }

    fun getTarotIllustId(topicNumber: Int) : Int {
        return when(topicNumber){
            0 -> R.drawable.illust_heartkey
            1 -> R.drawable.illust_study
            2 -> R.drawable.illust_dream
            3 -> R.drawable.illust_career
            else -> {
                R.drawable.illust_heartkey
            }
        }
    }
}