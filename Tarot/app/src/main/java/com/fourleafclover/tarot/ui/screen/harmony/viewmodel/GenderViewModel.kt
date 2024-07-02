package com.fourleafclover.tarot.ui.screen.harmony.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GenderViewModel: ViewModel() {

    private var pickedGender = mutableStateOf(-1)

    fun updatePickedGender(newPick: Int){
        pickedGender.value = newPick
    }

    fun isSelected(thisGender: Int): Boolean {
        return pickedGender.value == thisGender
    }

    fun isCompleted(): Boolean {
        return pickedGender.value != -1
    }
}