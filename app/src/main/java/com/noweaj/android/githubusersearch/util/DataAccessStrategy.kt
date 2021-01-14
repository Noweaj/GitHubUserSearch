package com.noweaj.android.githubusersearch.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.entity.SearchResult
import kotlinx.coroutines.Dispatchers

fun <T, A> performRemoteGetOperation(
        networkCall: suspend () -> Resource<T>,
        databaseQuery: suspend (T) -> Resource<A>
): LiveData<Resource<A>> =
        liveData(Dispatchers.Default) {
            emit(Resource.loading())
            val networkResult = networkCall.invoke()
            if(networkResult.status == Resource.Status.SUCCESS){
                val newReseult = databaseQuery(networkResult.data!!)
                emit(Resource.success(newReseult.data!!))
//                emit(Resource.success(networkResult.data!!))
            } else /*if(networkResult.status == Resource.Status.ERROR)*/{
                emit(Resource.error(networkResult.message!!, null))
            }
        }

fun performLocalInsertOperation(
        insertUser: suspend() -> Resource<Unit>
): LiveData<Resource<Unit>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val dbInsertResult = insertUser.invoke()
            if(dbInsertResult.status == Resource.Status.SUCCESS){
                emit(Resource.success(null))
            } else {
                emit(Resource.error(dbInsertResult.message!!, null))
            }
        }

fun performLocalDeleteOperation(
        deleteUser: suspend() -> Resource<Unit>
): LiveData<Resource<Unit>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val dbDeleteResult = deleteUser.invoke()
            if(dbDeleteResult.status == Resource.Status.SUCCESS){
                emit(Resource.success(null))
            } else {
                emit(Resource.error(dbDeleteResult.message!!, null))
            }
        }

fun performLocalGetAllOperation(
        getAllUser: suspend() -> Resource<List<GithubUser>>
): LiveData<Resource<List<GithubUser>>> =
        liveData {
            emit(Resource.loading())
            val getAllResult = getAllUser.invoke()
            when(getAllResult.status){
                Resource.Status.SUCCESS -> emit(Resource.success(getAllResult.data!!))
                else -> emit(Resource.error(getAllResult.message!!, null))
            }
        }
