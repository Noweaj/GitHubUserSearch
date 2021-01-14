package com.noweaj.android.githubusersearch.data.local

import android.util.Log
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.entity.SearchResult
import com.noweaj.android.githubusersearch.data.remote.BaseDataSource
import com.noweaj.android.githubusersearch.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserSearchLocalDataSource(
        private val githubUserDao: GithubUserDao
): BaseDataSource(){

    private val TAG = UserSearchLocalDataSource::class.java.simpleName

    suspend fun insert(githubUser: GithubUser): Resource<Unit> {
        githubUser.liked = true
        val insertJob: Job = CoroutineScope(Dispatchers.IO).launch {
            githubUserDao.insert(githubUser)
        }
        insertJob.join()
        return Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun contains(searchResult: SearchResult): Resource<SearchResult> {
        val userList = searchResult.items
        for(i in userList.indices){
            Log.d(TAG, "loop $i start")
            val containJob: Job = CoroutineScope(Dispatchers.IO).launch {
                userList[i].liked = githubUserDao.contains(userList[i].login)
                Log.d(TAG, "loop $i ongoing")
            }
            containJob.join()
            Log.d(TAG, "loop $i done")
        }

        for(i in userList.indices){
            Log.d(TAG, "${userList[i].login} / ${userList[i].liked}")
        }
        return Resource(Resource.Status.SUCCESS, SearchResult(searchResult.total_count, searchResult.incomplete_result, userList), null)
    }

    suspend fun delete(githubUser: GithubUser): Resource<Unit> {
        githubUser.liked = false
        var _githubUser: GithubUser? = null
        val getJob: Job = CoroutineScope(Dispatchers.IO).launch {
            _githubUser = githubUserDao.getUser(githubUser.login)
        }
        getJob.join()
        val deleteJob: Job = CoroutineScope(Dispatchers.IO).launch {
            githubUserDao.delete(_githubUser!!)
        }
        deleteJob.join()
        return Resource(Resource.Status.SUCCESS, null, null)
    }

    suspend fun getAll(): Resource<List<GithubUser>> {
        val githubUsers = mutableListOf<GithubUser>()
        val getAllJob: Job = CoroutineScope(Dispatchers.IO).launch {
            githubUsers.addAll(githubUserDao.getAll())
        }
        getAllJob.join()
        return Resource(Resource.Status.SUCCESS, githubUsers, null)
    }

}