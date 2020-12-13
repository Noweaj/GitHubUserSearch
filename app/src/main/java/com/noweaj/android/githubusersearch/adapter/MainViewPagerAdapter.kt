package com.noweaj.android.githubusersearch.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noweaj.android.githubusersearch.liked.LikedFragment
import com.noweaj.android.githubusersearch.search.SearchFragment
import java.lang.IndexOutOfBoundsException

const val SEARCH_INDEX = 0
const val LIKED_INDEX = 1

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        SEARCH_INDEX to { SearchFragment() },
        LIKED_INDEX to { LikedFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }


}