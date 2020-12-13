package com.noweaj.android.githubusersearch.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikedViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LikedViewModel() as T
    }
}