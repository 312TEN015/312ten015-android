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
data class MajorTopicData(
    @PrimaryKey(autoGenerate = true)
    val majorTopicId: Int = 0,
    val name: String    // 연애운
)
