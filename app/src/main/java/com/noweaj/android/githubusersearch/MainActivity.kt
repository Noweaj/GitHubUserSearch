package com.noweaj.android.githubusersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.noweaj.android.githubusersearch.adapter.LIKED_INDEX
import com.noweaj.android.githubusersearch.adapter.MainViewPagerAdapter
import com.noweaj.android.githubusersearch.adapter.SEARCH_INDEX
import com.noweaj.android.githubusersearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val tabLayout = binding.tlMain
        val viewPager = binding.vp2Main

        viewPager.adapter = MainViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = getTabTitle(pos)
        }.attach()
    }

    private fun getTabTitle(pos: Int): String? {
        return when(pos){
            SEARCH_INDEX -> "SEARCH"
            LIKED_INDEX -> "LIKED"
            else -> null
        }
    }
}