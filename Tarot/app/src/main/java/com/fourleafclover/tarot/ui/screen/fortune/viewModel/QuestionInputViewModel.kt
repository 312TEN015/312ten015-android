package com.fourleafclover.tarot.ui.screen.fortune.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class QuestionInputViewModel: ViewModel() {
    private var _answer1 = mutableStateOf(TextFieldValue(""))

    private var _answer2 = mutableStateOf(TextFieldValue(""))

    private var _answer3 = mutableStateOf(TextFieldValue(""))

    private var _maxChar = 50
    val maxChar
        get() = _maxChar


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
}