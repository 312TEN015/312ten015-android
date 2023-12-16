package com.fourleafclover.tarot.data

import android.util.Log


val TAROT_1 = "THE MAGICIAN"
val TAROT_2 = "THE HIGH PRIESTESS"
val TAROT_3 = "THE EMPRESS"
val TAROT_4 = "THE EMPEROR"
val TAROT_5 = "THE POPE"
val TAROT_6 = "THE LOVERS"
val TAROT_7 = "THE CHARIOT"
val TAROT_8 = "THE FORSE"
val TAROT_9 = "THE HERMIT"
val TAROT_10 = "THE WHEEL OF FORTUNE"
val TAROT_11 = "THE JUSTICE"
val TAROT_12 = "THE HANGED MAN"
val TAROT_13 = "THE DEATH"
val TAROT_14 = "THE TEMPERANCE"
val TAROT_15 = "THE DEVIL"
val TAROT_16 = "THE TOWER"
val TAROT_17 = "THE STAR"
val TAROT_18 = "THE MOON"
val TAROT_19 = "THE SUN"
val TAROT_20 = "THE JUDGEMENT"
val TAROT_21 = "THE WORLD"

/* 선택한 대주제 인덱스 */
// 0 -> 연애운
// 1 -> 학업운
// 2 -> 소망운
// 3 -> 직업운
var pickedTopicNumber = 0

/* 선택한 대주제 저장 */
data class TarotSubjectData(
    val majorTopic: String = "",
    val majorQuestion: String = "",
    val subQuestions : ArrayList<String> = arrayListOf()
)

val SubjectLove = TarotSubjectData("연애운", majorQuestion = "그 사람의 마음은?", arrayListOf("그 사람과의 첫만남은 어땠나요?", "그 사람에 대한 현재 나의 감정은?", "그 사람과 어떤 관계가 되고 싶나요?"))
val SubjectStudy = TarotSubjectData("학업운", majorQuestion = "시험 결과가 궁금해!", arrayListOf("어떤 시험을 준비하고 있나요?", "시험을 위해 어떤 노력을 하고 있나요?", "시험에서 어떤 결과를 얻고 싶나요?"))
val SubjectDream = TarotSubjectData("소망운", majorQuestion = "꿈이 이뤄졌으면!", arrayListOf("이루고 싶은 꿈이 무엇인가요?", "그 꿈을 위해 어떤 노력을 하고 있나요?", "꿈을 이루는데 있어 가장 장애물이 되는 것이 무엇인가요?"))
val SubjectJob = TarotSubjectData("취업운", majorQuestion = "취업할 수 있을까?", arrayListOf("어떤 직무 취업을 준비하고 있나요?", "취업을 위해 어떤 노력을 하고 있나요?", "취업 준비 중에 어떤 부분이 가장 어려운가요?"))

fun getPickedTopic(): TarotSubjectData {
    return when (pickedTopicNumber){
        0 -> SubjectLove
        1 -> SubjectStudy
        2 -> SubjectDream
        3 -> SubjectJob
        else -> {
            Log.e("tarotError", "error getPickedTopic(). pickedTopicNumber: $pickedTopicNumber")
            TarotSubjectData()
        }
    }
}

val tarotInputDto = TarotInputDto("", "", "", arrayListOf(0, 0, 0))

val tarotOutputDto = TarotOutputDto("0", arrayListOf(), null)