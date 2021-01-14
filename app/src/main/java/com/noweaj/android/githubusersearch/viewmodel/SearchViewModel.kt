package com.noweaj.android.githubusersearch.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository
import java.util.*

class SearchViewModel(
    private val repository: UserSearchRepository
): ViewModel() {

    private val TAG = SearchViewModel::class.java.simpleName
    init{
        Log.d(TAG, "onCreate")
    }

    private var timer: Timer? = null

    val textWatcher = object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            timer?.cancel()
            timer = null
        }

        override fun afterTextChanged(s: Editable?) {
            timer = Timer()
            timer!!.schedule(object: TimerTask(){
                override fun run() {
                    // do search
                    Log.d(TAG, s.toString())
//                    searchResult.switchMap { repository.searchGithubUser(s.toString()) }
//                    repository.searchGithubUser(s.toString())
//                    _searchResult = repository.searchGithubUser(s.toString())
                    userId.postValue(s.toString())
                    Log.d(TAG, "searchGithubUser called")
                }
            }, 1000)
        }
    }

//    val searchResult = MutableLiveData<Resource<SearchResult>>()
//    val searchResult = repository.searchGithubUser("35235")
    private val userId = MutableLiveData<String>()
//    val searchResult : LiveData<Resource<SearchResult>>
//        get() = _searchResult
//    private val _searchResult = userId.switchMap {
//        repository.searchGithubUser(it)
//    }

    val searchResult = userId.switchMap {
        repository.searchGithubUser(it)
    }

    private val clickResult_ = MutableLiveData<GithubUser>()
    val clickResult = clickResult_.switchMap {
        Log.d(TAG, "insertingLikedGithubUser: ${it.login}")
        if(it.liked){
            repository.deleteLikedGithubUser(it)
        } else {
            repository.insertLikedGithubUser(it)
        }
    }

    fun onLikedButtonClicked(githubUser: GithubUser){
        Log.d(TAG, "onLikedButtonClicked: ${githubUser.login}")
//        repository.insertLikedGithubUser(githubUser)
        clickResult_.postValue(githubUser)
    }
}