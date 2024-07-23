package com.fourleafclover.tarot.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.fourleafclover.tarot.R
import com.fourleafclover.tarot.SubjectDream
import com.fourleafclover.tarot.SubjectHarmony
import com.fourleafclover.tarot.SubjectJob
import com.fourleafclover.tarot.SubjectLove
import com.fourleafclover.tarot.SubjectStudy
import com.fourleafclover.tarot.SubjectToday
import com.fourleafclover.tarot.data.TarotSubjectData


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
            Log.e("tarotError", "error getPath(). pickedTopicNumber: $number")
            ""
        }
    }
}

fun getPickedTopic(topicNumber: Int): TarotSubjectData {
    return when (topicNumber){
        0 -> SubjectLove
        1 -> SubjectStudy
        2 -> SubjectDream
        3 -> SubjectJob
        4 -> SubjectToday
        5 -> SubjectHarmony
        else -> {
            Log.e("tarotError", "error getPickedTopic(). pickedTopicNumber: $topicNumber selectedTarotResult: $topicNumber")
            TarotSubjectData()
        }
    }
}