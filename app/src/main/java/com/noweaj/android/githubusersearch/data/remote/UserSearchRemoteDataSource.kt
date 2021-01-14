package com.noweaj.android.githubusersearch.data.remote

class UserSearchRemoteDataSource(
    private val userSearchService: UserSearchService
): BaseDataSource() {
    suspend fun searchUser(user: String) = getResult { userSearchService.searchUser(user) }
}