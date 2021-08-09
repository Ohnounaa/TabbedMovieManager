package com.example.tabbedmoviemanager

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tabbedmoviemanager.ui.main.SectionsPagerAdapter
import com.example.tabbedmoviemanager.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, lifecycle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when(position) {
                0 -> {tab.text = "New And Trending"}
                1 -> {tab.text = "Your Favorites"}
            }
            viewPager.currentItem = tab.position
        }.attach()
    }
}