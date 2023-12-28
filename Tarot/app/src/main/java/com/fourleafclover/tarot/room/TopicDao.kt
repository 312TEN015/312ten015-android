package com.fourleafclover.tarot.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMajorTopic(item: TopicData)

    @Update
    suspend fun updateMajorTopic(item: TopicData)

    @Delete
    suspend fun deleteMajorTopic(item: TopicData)

    /* --------------------------------------------- */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubTopic(item: SubTopicData)

    @Update
    suspend fun updateSubTopic(item: SubTopicData)

    @Delete
    suspend fun deleteSubTopic(item: SubTopicData)

    @Query("SELECT * from subTopics")
    fun getAllSubTopics(): Flow<SubTopicData>
}