package com.noweaj.android.githubusersearch.liked

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.noweaj.android.githubusersearch.databinding.FragmentLikedBinding
import com.noweaj.android.githubusersearch.util.InjectionUtil

class LikedFragment: Fragment() {

    private val TAG = LikedFragment::class.java.simpleName

    private val viewModel: LikedViewModel by viewModels {
        InjectionUtil.provideLikedViewModelFactory()
    }
    private lateinit var binding: FragmentLikedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }
}