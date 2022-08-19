package com.example.simbirsoftpracticeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simbirsoftpracticeapp.data.database.categories.CategoriesDao
import com.example.simbirsoftpracticeapp.data.database.categories.CategoryEntity
import com.example.simbirsoftpracticeapp.data.database.events.CharityEventEntity
import com.example.simbirsoftpracticeapp.data.database.events.EventsDao
import com.example.simbirsoftpracticeapp.common.Constants

@Database(entities = [CharityEventEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charityEventsDao(): EventsDao

    abstract fun categoriesDao(): CategoriesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
