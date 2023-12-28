package com.fourleafclover.tarot.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertResult(item: ResultData)

    @Update
    suspend fun updateResult(item: ResultData)

    @Delete
    suspend fun deleteResult(item: ResultData)

    @Query("SELECT * from results")
    fun getAllResults(): Flow<ResultData>
}