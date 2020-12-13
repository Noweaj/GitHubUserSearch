package com.noweaj.android.githubusersearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LikedDao::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun likedDao(): LikedDao

    companion object{
        private val DB_NAME = "liked-db"
        private val instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}