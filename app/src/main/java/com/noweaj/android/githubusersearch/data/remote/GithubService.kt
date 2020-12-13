package com.noweaj.android.githubusersearch.data.remote

import com.noweaj.android.githubusersearch.data.entity.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/users")
    fun searchUser(
        @Query("q") user: String
    ): Call<SearchResult>
}