package com.noweaj.android.githubusersearch.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.util.Resource

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM githubuser")
    fun getAll(): List<GithubUser>

    @Query("SELECT * FROM githubuser where login = :login")
    fun getUser(login: String): GithubUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUser)

    @Query("SELECT EXISTS(SELECT * FROM githubuser where login = :login)")
    fun contains(login: String): Boolean

    @Delete
    fun delete(user: GithubUser)

    @Query("DELETE FROM githubuser")
    fun deleteAll()
}