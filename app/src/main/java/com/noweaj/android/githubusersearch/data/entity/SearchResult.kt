package com.noweaj.android.githubusersearch.data.entity

data class SearchResult(
    val total_count: Int,
    val incomplete_result: Boolean,
    val items: List<GithubUser>
)