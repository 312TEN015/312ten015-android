package com.fourleafclover.tarot.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


val tarotResultListKey = "savedTarotList"
val sharedPreferencesKey = "saved_tarots"
class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)


    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun getTarotResultArray(): ArrayList<String> {
        val stringPrefs = getString(tarotResultListKey, "[]")
        return GsonBuilder().create().fromJson(
            stringPrefs, object: TypeToken<ArrayList<String>>(){}.type
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
}