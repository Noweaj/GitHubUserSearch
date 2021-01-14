package com.noweaj.android.githubusersearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.noweaj.android.githubusersearch.adapter.UserListAdapter
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.local.AppDatabase
import com.noweaj.android.githubusersearch.data.local.UserSearchLocalDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchRemoteDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchService
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository
import com.noweaj.android.githubusersearch.databinding.FragmentLikedBinding
import com.noweaj.android.githubusersearch.viewmodel.LikedViewModel
import com.noweaj.android.githubusersearch.util.GithubUserItemListener
import com.noweaj.android.githubusersearch.util.InjectionUtil
import com.noweaj.android.githubusersearch.util.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LikedFragment: Fragment(), GithubUserItemListener {

    private val TAG = LikedFragment::class.java.simpleName

    private val viewModel: LikedViewModel by viewModels {
        InjectionUtil.provideLikedViewModelFactory(
                UserSearchRepository(
                        UserSearchRemoteDataSource(Retrofit.Builder()
                                .baseUrl("https://api.github.com")
                                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                                .build()
                                .create(UserSearchService::class.java)),
                        UserSearchLocalDataSource(
                                AppDatabase.getInstance(requireContext()).likedDao()
                        )
                )
        )
    }
    private lateinit var binding: FragmentLikedBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setView()
        observe()

        return binding.root
    }

    private fun setView(){
        val linearLayoutManager = LinearLayoutManager(requireActivity().applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvLikedResult.layoutManager = linearLayoutManager

        adapter = UserListAdapter(this)
        binding.rvLikedResult.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel!!.updateEntities()
    }

    private fun observe(){
        binding.viewModel!!.likedUsers.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.LOADING -> {
                    Log.d(TAG, "LOADING")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, "SUCCESS")
                    adapter.setData(it.data!!)
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, "ERROR")
                }
            }
        })

        binding.viewModel!!.deleteResult.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.LOADING -> {
                    Log.d(TAG, "LOADING")
                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, "SUCCESS")
                    adapter.refreshData()
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, "ERROR")
                }
            }
        })
    }

    override fun onItemClick(user: GithubUser) {
        binding.viewModel!!.onItemClicked(user)
    }
}