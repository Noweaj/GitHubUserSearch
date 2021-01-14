package com.noweaj.android.githubusersearch.ui.core

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    abstract fun setObserver()
    abstract fun removeObserver()

    override fun onResume() {
        super.onResume()
        setObserver()
    }

    override fun onPause() {
        super.onPause()
        removeObserver()
    }
}