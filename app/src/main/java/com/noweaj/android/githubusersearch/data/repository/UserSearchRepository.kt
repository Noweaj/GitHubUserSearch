package com.noweaj.android.githubusersearch.data.repository

import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.entity.SearchResult
import com.noweaj.android.githubusersearch.data.local.GithubUserDao
import com.noweaj.android.githubusersearch.data.local.UserSearchLocalDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchRemoteDataSource
import com.noweaj.android.githubusersearch.util.*
import javax.inject.Inject

class UserSearchRepository(
    private val remoteDataSource: UserSearchRemoteDataSource,
    private val localDataSource: UserSearchLocalDataSource
) {
    /**
     * Remote
     */
    fun searchGithubUser(id: String) = performRemoteGetOperation (
        networkCall = { remoteDataSource.searchUser(id) },
        databaseQuery = { localDataSource.contains(it) }
    )

    /**
     * Local
     */
    fun insertLikedGithubUser(githubUser: GithubUser) = performLocalInsertOperation (
        insertUser = { localDataSource.insert(githubUser) }
    )

    fun deleteLikedGithubUser(githubUser: GithubUser) = performLocalDeleteOperation(
        deleteUser = { localDataSource.delete(githubUser) }
    )

    fun getLikedGithubUser() = performLocalGetAllOperation(
        getAllUser = { localDataSource.getAll() }
    )
}