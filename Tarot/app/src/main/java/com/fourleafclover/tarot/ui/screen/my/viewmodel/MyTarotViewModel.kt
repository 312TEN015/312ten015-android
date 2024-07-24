package com.fourleafclover.tarot.ui.screen.my.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.fourleafclover.tarot.data.TarotOutputDto

class MyTarotViewModel: ViewModel() {

    var _selectedTarotResult = mutableStateOf(TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null))
    val selectedTarotResult get() = _selectedTarotResult.value


    var _myTarotResults = mutableStateListOf<TarotOutputDto>()
    val myTarotResults get() = _myTarotResults

    fun selectItem(idx: Int){
        _selectedTarotResult.value = _myTarotResults[idx]
    }

    fun selectItem(item: TarotOutputDto){
        _selectedTarotResult.value = item
    }

    fun deleteSelectedItem(){
        val itemToDelete = _myTarotResults.find { it.tarotId == _selectedTarotResult.value.tarotId }
        _myTarotResults.remove(itemToDelete)
    }

    fun setMyTarotResults(results: ArrayList<TarotOutputDto>){
        _myTarotResults.clear()
        _myTarotResults.addAll(results)
        Log.d("", _myTarotResults.joinToString(" "))
    }
}