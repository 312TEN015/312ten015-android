package com.fourleafclover.tarot.data

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.fourleafclover.tarot.R


fun getCardImageId(localContext: Context, cardNumber: String): Int {
    val resources: Resources = localContext.resources
    return resources.getIdentifier("tarot_$cardNumber", "drawable", localContext.packageName)
}

fun getSubjectImoji(localContext: Context, number: Int):String {
    val resources: Resources = localContext.resources
    return when(number){
        0 -> resources.getString(R.string.imoji_love)
        1 -> resources.getString(R.string.imoji_study)
        2 -> resources.getString(R.string.imoji_dream)
        3 -> resources.getString(R.string.imoji_job)
        else -> {
            Log.e("tarotError", "error getPath(). pickedTopicNumber: $pickedTopicNumber")
            ""
        }
    }
}