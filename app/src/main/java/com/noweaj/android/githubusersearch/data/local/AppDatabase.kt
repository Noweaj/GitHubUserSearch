package com.noweaj.android.githubusersearch.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.noweaj.android.githubusersearch.data.entity.GithubUser

@Database(entities = [GithubUser::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun likedDao(): GithubUserDao

    companion object{
        private val TAG = AppDatabase::class.java.simpleName
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
            ).addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Log.d(TAG, "DB version ${db.version} created")
                }
            }).build()
        }
    }
}