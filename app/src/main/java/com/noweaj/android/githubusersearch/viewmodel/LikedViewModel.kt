package com.noweaj.android.githubusersearch.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository

class LikedViewModel(
    private val repository: UserSearchRepository
): ViewModel() {

    private val TAG = LikedViewModel::class.java.simpleName
    init{
        Log.d(TAG, "onCreate")
    }

    private val likedUsers_ = MutableLiveData<Unit>()
    val likedUsers = likedUsers_.switchMap {
        repository.getLikedGithubUser()
    }

    private val deleteResult_ = MutableLiveData<GithubUser>()
    val deleteResult = deleteResult_.switchMap {
        if(it.liked){
            repository.deleteLikedGithubUser(it)
        } else {
            repository.insertLikedGithubUser(it)
        }
    }

    fun onItemClicked(user: GithubUser){
        deleteResult_.postValue(user)
    }

    fun updateEntities(){
        likedUsers_.postValue(null)
    }
}