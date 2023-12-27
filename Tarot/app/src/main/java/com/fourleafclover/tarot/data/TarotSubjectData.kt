package com.fourleafclover.tarot.data

/* 선택한 대주제 정보 */
data class TarotSubjectData(
        val majorTopic: String = "",
        val majorQuestion: String = "",
        val subQuestions : ArrayList<String> = arrayListOf(),
        val placeHolders : ArrayList<String> = arrayListOf()
)
