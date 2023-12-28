package com.fourleafclover.tarot.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fourleafclover.tarot.data.TarotOutputDto

/* 결과 테이블 */
@Entity(tableName = "results")
data class ResultData(
    @PrimaryKey(autoGenerate = true)
    val localResultId: Int = 0,
    val tarotOutputDto: TarotOutputDto
)