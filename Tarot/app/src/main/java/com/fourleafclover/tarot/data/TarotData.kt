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
    val subQuestions : ArrayList<String> = arrayListOf(),
    val placeHolders : ArrayList<String> = arrayListOf()
)

val SubjectLove = TarotSubjectData("연애운",
    majorQuestion = "그 사람의 마음은?",
    arrayListOf("그 사람과의 첫만남은 어땠나요?", "그 사람에 대한 현재 나의 감정은?", "그 사람과 어떤 관계가 되고 싶나요?"),
    arrayListOf("대학교 동아리에서 다정하게 챙겨주는 모습을 보고 반해버렸습니다.", "그 사람이 너무 좋아서 생각만 해도 심장이 빨리 뛰는것 같아요.", "서로 죽고 못사는 애인 관계가 되고 싶어요."))
val SubjectStudy = TarotSubjectData("학업운",
    majorQuestion = "시험 결과가 궁금해!",
    arrayListOf("어떤 시험을 준비하고 있나요?", "시험을 위해 어떤 노력을 하고 있나요?", "시험에서 어떤 결과를 얻고 싶나요?"),
    arrayListOf("대학원 졸업 시험을 준비하고 있습니다.", "매일 독서실에서 8시간씩 공부를 하고 있습니다.", "졸업 시험에 통과해서, 무사히 대학원을 졸업하고 싶습니다."))
val SubjectDream = TarotSubjectData("소망운", majorQuestion = "꿈이 이뤄졌으면!",
    arrayListOf("이루고 싶은 꿈이 무엇인가요?", "그 꿈을 위해 어떤 노력을 하고 있나요?", "꿈을 이루는데 있어 가장 장애물이 되는 것이 무엇인가요?"),
    arrayListOf("30살이 되기 전에 세계 일주를 하고 싶습니다.", "관심이 있는 나라별 여행 정보를 개인 블로그에 꾸준히 모으고 있습니다.", "회사가 항상 바빠서 장기 여행 일정을 잡는 것이 힘듭니다."))
val SubjectJob = TarotSubjectData("취업운",
    majorQuestion = "취업할 수 있을까?",
    arrayListOf("어떤 직무 취업을 준비하고 있나요?", "취업을 위해 어떤 노력을 하고 있나요?", "취업 준비 중에 어떤 부분이 가장 어려운가요?"),
    arrayListOf("IT회사 개발 직무 취업을 준비하고 있습니다.", "분기별로 부트캠프에 참여하여 개발 역량을 키우고 있고, 주 1회 개발 스터디를 하고 있습니다.", "회사별 코딩테스트를 짧은 기간 안에 준비하는것이 가장 어렵습니다."))

fun getPickedTopic(topicNumber: Int): TarotSubjectData {
    return when (topicNumber){
        0 -> SubjectLove
        1 -> SubjectStudy
        2 -> SubjectDream
        3 -> SubjectJob
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
