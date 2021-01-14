package com.noweaj.android.githubusersearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository

class SearchViewModelFactory(
    private val userSearchRepository: UserSearchRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(userSearchRepository) as T
    }
}