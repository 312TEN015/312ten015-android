package com.fourleafclover.tarot.utils

import android.util.Log
import com.fourleafclover.tarot.pickedTopicNumber

fun getPath() : String {
    return when(pickedTopicNumber){
        0 -> "love"
        1 -> "study"
        2 -> "dream"
        3 -> "job"
        4 -> "today"
        else -> {
            Log.e("tarotError", "error getPath(). pickedTopicNumber: $pickedTopicNumber")
            ""
        }
    }
}