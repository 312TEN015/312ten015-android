package com.fourleafclover.tarot.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.fourleafclover.tarot.MyApplication
import com.fourleafclover.tarot.data.TarotOutputDto
import com.fourleafclover.tarot.myTarotResults
import com.fourleafclover.tarot.selectedTarotResult
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


const val tarotResultListKey = "savedTarotList"
const val sharedPreferencesKey = "saved_tarots"
const val onBoardingKey = "onBoardingCompleted"
const val pickCardIndicateKey = "pickCardIndicateCompleted"

class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)


    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun getTarotResultArray(): ArrayList<String> {
        val stringPrefs = getString(tarotResultListKey, "[]")
        return GsonBuilder().create().fromJson(
            stringPrefs,
            object: TypeToken<ArrayList<String>>(){}.type
        )
    }

    fun saveTarotResult(arrayListPrefs: ArrayList<String>) {
        val stringPrefs = GsonBuilder().create().toJson(
            arrayListPrefs,
            object : TypeToken<ArrayList<String>>() {}.type
        )
        prefs.edit().putString(tarotResultListKey, stringPrefs).apply() // SharedPreferences에 push

        Log.d("", "saveTarotResult: ${getString(tarotResultListKey, "[]")}")
    }


    fun deleteAllTarotResults(){
        prefs.edit().remove(tarotResultListKey).commit()
    }

    fun deleteTarotResult(){
//        val updatedArray = MyApplication.prefs
//            .getTarotResultArray()
//            .filter { it != selectedTarotResult.tarotId }
        myTarotResults = myTarotResults.filter { it.tarotId != selectedTarotResult.tarotId } as ArrayList<TarotOutputDto>

        MyApplication.prefs.saveTarotResult(myTarotResults.map { it.tarotId } as ArrayList<String>)
    }

    fun isOnBoardingComplete(): Boolean{
        return prefs.getBoolean(onBoardingKey, false)
    }

    fun setOnBoardingComplete() {
        prefs.edit().putBoolean(onBoardingKey, true).apply()
    }

    fun setPickCardIndicateComplete() {
        prefs.edit().putBoolean(pickCardIndicateKey, false).apply()
    }

    fun isPickCardIndicateComplete(): Boolean {
        return prefs.getBoolean(pickCardIndicateKey, true)
    }

    fun deleteIsPickCardIndicateComplete(){
        prefs.edit().remove(pickCardIndicateKey).commit()
    }
}