package com.fourleafclover.tarot.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/* 대주제 테이블 */
// 0 -> 연애운
// 1 -> 학업운
// 2 -> 소망운
// 3 -> 직업운
// 4 -> 오늘의 운세
@Entity(tableName = "majorTopics")
data class TopicData(
    @PrimaryKey(autoGenerate = true)
    val majorTopicId: Int = 0,
    val name: String    // 연애운
)

/* 소주제 테이블 */
@Entity(tableName = "subTopics")
data class SubTopicData(
    @PrimaryKey(autoGenerate = true)
    val subTopicId: Int = 0,
    val majorTopicId: Int = 0,
    val majorQuestion: String = "", // "그 사람의 마음은?"
    val subQuestions : ArrayList<String> = arrayListOf(),   // arrayListOf("그 사람과의 첫만남은 어땠나요?", "그 사람에 대한 현재 나의 감정은?", "그 사람과 어떤 관계가 되고 싶나요?")
    val placeHolders : ArrayList<String> = arrayListOf()    // arrayListOf("대학교 동아리에서 다정하게 챙겨주는 모습을 보고 반해버렸습니다.", "그 사람이 너무 좋아서 생각만 해도 심장이 빨리 뛰는것 같아요.", "서로 죽고 못사는 애인 관계가 되고 싶어요."
)
