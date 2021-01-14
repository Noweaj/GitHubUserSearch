package com.noweaj.android.githubusersearch.data.remote

import com.noweaj.android.githubusersearch.util.Resource
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {

    private val TAG = BaseDataSource::class.java.simpleName

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try{
            val response = call()
            if(response.isSuccessful){
                val body = response.body()
                body?.let {
                    return Resource.success(it)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception){
            return error(e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }
}