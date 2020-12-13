package com.noweaj.android.githubusersearch.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noweaj.android.githubusersearch.databinding.FragmentSearchBinding
import com.noweaj.android.githubusersearch.util.InjectionUtil

class SearchFragment: Fragment() {

    private val TAG = SearchFragment::class.java.simpleName

    private val viewModel: SearchViewModel by viewModels {
        InjectionUtil.provideSearchViewModelFactory()
    }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }
}