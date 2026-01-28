package com.sayeedjoy.gymbro.weight

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sayeedjoy.gymbro.data.WeightDao

@Database(
    entities = [WeightEntryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GymBroDatabase : RoomDatabase() {

    abstract fun weightDao(): WeightDao

    companion object {
        @Volatile private var INSTANCE: GymBroDatabase? = null

        fun get(context: Context): GymBroDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GymBroDatabase::class.java,
                    "gymbro.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
