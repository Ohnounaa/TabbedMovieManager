package com.example.tabbedmoviemanager.ui.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabbedmoviemanager.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)


class SectionsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
   FragmentStateAdapter(fm, lifecycle) {


    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
       lateinit var fragment: Fragment
        when(position) {
            0 -> {
                fragment = HomePageFragment()
            }
            1 -> {
                fragment = FavoritesFragment()
            }
        }
        return  fragment
    }
}