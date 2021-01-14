package com.noweaj.android.githubusersearch.util

import android.content.Context
import com.google.gson.GsonBuilder
import com.noweaj.android.githubusersearch.data.local.AppDatabase
import com.noweaj.android.githubusersearch.data.local.UserSearchLocalDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchRemoteDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchService
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository
import com.noweaj.android.githubusersearch.viewmodel.LikedViewModelFactory
import com.noweaj.android.githubusersearch.viewmodel.SearchViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InjectionUtil {

    fun provideRepository(context: Context): UserSearchRepository{
        return UserSearchRepository(
                UserSearchRemoteDataSource(Retrofit.Builder()
                        .baseUrl("https://api.github.com")
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                        .build()
                        .create(UserSearchService::class.java)),
                UserSearchLocalDataSource(
                        AppDatabase.getInstance(context).likedDao()
                )
        )
    }

    fun provideSearchViewModelFactory(
        userSearchRepository: UserSearchRepository
    ): SearchViewModelFactory {
        return SearchViewModelFactory(userSearchRepository)
    }

    fun provideLikedViewModelFactory(
        userSearchRepository: UserSearchRepository
    ): LikedViewModelFactory {
        return LikedViewModelFactory(userSearchRepository)
    }
}