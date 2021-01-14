package com.noweaj.android.githubusersearch.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "githubuser")
data class GithubUser(
    @SerializedName("login")
    @PrimaryKey val login: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("avatar_url")
    val avatar_url: String,
    @SerializedName("html_url")
    val html_url: String,
    @SerializedName("score")
    val score: Double,
    var liked: Boolean
)