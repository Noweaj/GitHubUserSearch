package com.noweaj.android.githubusersearch.util

import com.noweaj.android.githubusersearch.data.entity.GithubUser

interface GithubUserItemListener {
    fun onItemClick(user: GithubUser)
}