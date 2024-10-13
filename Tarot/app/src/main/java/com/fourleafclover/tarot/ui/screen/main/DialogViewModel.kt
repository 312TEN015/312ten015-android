package com.fourleafclover.tarot.ui.screen.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(): ViewModel() {
    private var _openDialog = mutableStateOf(false)
    val openDialog get() = _openDialog.value

    fun clear() {
        _openDialog = mutableStateOf(false)
    }

    fun openDialog() {
        _openDialog.value = true
    }

    fun closeDialog() {
        _openDialog.value = false
    }

}