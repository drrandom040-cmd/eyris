package com.elsewhere.eyris.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elsewhere.eyris.data.local.room.dao.BusinessDao
import com.elsewhere.eyris.data.local.room.entity.BusinessEntity
import com.elsewhere.eyris.data.local.room.entity.ContactedBusinessEntity
import com.elsewhere.eyris.data.local.room.entity.UserEntity
import com.elsewhere.eyris.data.local.room.entity.SearchHistoryEntity

@Database(
    entities = [
        BusinessEntity::class,
        ContactedBusinessEntity::class,
        UserEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessDao(): BusinessDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eyris_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
