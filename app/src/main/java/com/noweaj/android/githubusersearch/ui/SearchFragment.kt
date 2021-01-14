package com.noweaj.android.githubusersearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.noweaj.android.githubusersearch.adapter.UserListAdapter
import com.noweaj.android.githubusersearch.data.entity.GithubUser
import com.noweaj.android.githubusersearch.data.entity.SearchResult
import com.noweaj.android.githubusersearch.data.local.AppDatabase
import com.noweaj.android.githubusersearch.data.local.UserSearchLocalDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchRemoteDataSource
import com.noweaj.android.githubusersearch.data.remote.UserSearchService
import com.noweaj.android.githubusersearch.data.repository.UserSearchRepository
import com.noweaj.android.githubusersearch.databinding.FragmentSearchBinding
import com.noweaj.android.githubusersearch.ui.core.BaseFragment
import com.noweaj.android.githubusersearch.viewmodel.SearchViewModel
import com.noweaj.android.githubusersearch.util.GithubUserItemListener
import com.noweaj.android.githubusersearch.util.InjectionUtil
import com.noweaj.android.githubusersearch.util.Resource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment: BaseFragment(), GithubUserItemListener {

    private val TAG = SearchFragment::class.java.simpleName

    private val viewModel: SearchViewModel by viewModels {
        InjectionUtil.provideSearchViewModelFactory(
                InjectionUtil.provideRepository(requireContext())
        )
    }
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        setView()
        return binding.root
    }

    private fun setView(){
        val linearLayoutManager = LinearLayoutManager(requireActivity().applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvSearchResult.layoutManager = linearLayoutManager

        adapter = UserListAdapter(this)
        binding.rvSearchResult.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData()
    }

    private lateinit var observerSearchResult: Observer<Resource<SearchResult>>
    private lateinit var observerClickResult: Observer<Resource<Unit>>

    override fun setObserver() {
        observerSearchResult = Observer {
            when(it.status){
                Resource.Status.LOADING -> {
                    Log.d(TAG, "LOADING")
//                    adapter.setData(emptyList())
                    binding.pbSearchStatus.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, "SUCCESS")
                    adapter.setData(it.data!!.items)
                    binding.pbSearchStatus.visibility = View.INVISIBLE
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, "ERROR")
                }
            }
        }
        binding.viewModel!!.searchResult.observe(viewLifecycleOwner, observerSearchResult)

        observerClickResult = Observer {
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
        }
        binding.viewModel!!.clickResult.observe(viewLifecycleOwner, observerClickResult)
    }

    override fun removeObserver() {
        binding.viewModel!!.searchResult.removeObserver(observerSearchResult)
        binding.viewModel!!.clickResult.removeObserver(observerClickResult)
    }

    override fun onItemClick(githubUser: GithubUser) {
        Log.d(TAG, "onItemClicked: ${githubUser.login}")
        binding.viewModel!!.onLikedButtonClicked(githubUser)
    }
}