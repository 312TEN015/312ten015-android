package com.fourleafclover.tarot.ui.screen.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.data.TarotOutputDto

class DialogViewModel: ViewModel() {
    private var _openDialog = mutableStateOf(false)
    val openDialog get() = _openDialog.value

    fun openDialog() {
        _openDialog.value = true
    }

    fun closeDialog() {
        _openDialog.value = false
    }

}