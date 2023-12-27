package com.fourleafclover.tarot.data

import android.util.Log
import com.fourleafclover.tarot.constant.*

val SubjectLove = TarotSubjectData(
        loveFortune,
        loveMajorQuestion,
        arrayListOf(loveSubQuestion1, loveSubQuestion2, loveSubQuestion3),
        arrayListOf(lovePlaceHolder1, lovePlaceHolder2, lovePlaceHolder3)
)
val SubjectStudy = TarotSubjectData(
        studyFortune,
        studyMajorQuestion,
        arrayListOf(studySubQuestion1, studySubQuestion2, studySubQuestion3),
        arrayListOf(studyPlaceHolder1, studyPlaceHolder2, studyPlaceHolder3)
)
val SubjectDream = TarotSubjectData(
        dreamFortune,
        dreamMajorQuestion,
        arrayListOf(dreamSubQuestion1, dreamSubQuestion2, dreamSubQuestion3),
        arrayListOf(dreamPlaceHolder1, dreamPlaceHolder2, dreamPlaceHolder3)
)
val SubjectJob = TarotSubjectData(
        jobFortune,
        jobMajorQuestion,
        arrayListOf(jobSubQuestion1, jobSubQuestion2, jobSubQuestion3),
        arrayListOf(jobPlaceHolder1, jobPlaceHolder2, jobPlaceHolder3)
)
val SubjectToday = TarotSubjectData(
        todayFortune,
        todayMajorQuestion,
        arrayListOf(),
        arrayListOf()
)

/* 선택한 대주제 인덱스 */
// 0 -> 연애운
// 1 -> 학업운
// 2 -> 소망운
// 3 -> 직업운
// 4 -> 오늘의 운세
var pickedTopicNumber = 0

fun getPickedTopic(topicNumber: Int): TarotSubjectData {
    return when (topicNumber){
        0 -> SubjectLove
        1 -> SubjectStudy
        2 -> SubjectDream
        3 -> SubjectJob
        4 -> SubjectToday
        else -> {
            Log.e("tarotError", "error getPickedTopic(). pickedTopicNumber: $pickedTopicNumber selectedTarotResult: $pickedTopicNumber")
            TarotSubjectData()
        }
    }
}

val tarotInputDto = TarotInputDto("", "", "", arrayListOf(0, 0, 0))

val tarotOutputDto = TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null)

var selectedTarotResult = TarotOutputDto("0", 0, arrayListOf(), "", arrayListOf(), null)

var myTarotResults = arrayListOf<TarotOutputDto>()

val entireCards = arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21)
fun getRandomCards(): List<Int> { return entireCards.toMutableList().shuffled() }
