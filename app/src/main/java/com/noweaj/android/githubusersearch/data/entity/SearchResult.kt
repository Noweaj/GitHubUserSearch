package com.noweaj.android.githubusersearch.data.entity

import com.google.gson.annotations.SerializedName

data class SearchResult(
        @SerializedName("total_count")
    val total_count: Int,
        @SerializedName("incomplete_results")
    val incomplete_result: Boolean,
        @SerializedName("items")
    val items: List<GithubUser>
)