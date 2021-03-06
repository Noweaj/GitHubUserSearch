package com.noweaj.android.githubusersearch.data.remote

import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.entity.SearchResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserSearchService {
    @GET("/search/users")
    suspend fun searchUser(
        @Query("q") user: String
    ): Response<SearchResult>
}