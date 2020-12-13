package com.noweaj.android.githubusersearch.util

import com.noweaj.android.githubusersearch.liked.LikedViewModelFactory
import com.noweaj.android.githubusersearch.search.SearchViewModelFactory

object InjectionUtil {

    fun provideSearchViewModelFactory(): SearchViewModelFactory {
        return SearchViewModelFactory()
    }

    fun provideLikedViewModelFactory(): LikedViewModelFactory {
        return LikedViewModelFactory()
    }
}