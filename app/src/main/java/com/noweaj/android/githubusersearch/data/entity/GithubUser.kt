package com.noweaj.android.githubusersearch.data.entity

data class GithubUser(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val html_url: String,
    val score: Double
)