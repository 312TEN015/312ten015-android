package com.fourleafclover.tarot.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MajorTopicData::class, SubTopicData::class],
    version = 1,
    exportSchema = false
)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao

    companion object {
        @Volatile
        private var Instance: TarotDatabase? = null

        fun getDatabase(context: Context): TarotDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TarotDatabase::class.java, "tarot_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}