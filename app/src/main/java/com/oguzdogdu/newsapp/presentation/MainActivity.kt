package com.oguzdogdu.newsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.ActivityMainBinding
import com.oguzdogdu.newsapp.presentation.fragments.newsfragment.NewsFragment
import com.oguzdogdu.newsapp.presentation.fragments.searchfragment.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(
            binding.toolbar
        )
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.newsFragmentBottom ->{
                    NewsFragment()
                }
                R.id.searchFragmentBottom -> {

                    SearchFragment()
                }


            }
        }
        binding.bottomNavBar.apply {
            setMenuOrientation(ChipNavigationBar.MenuOrientation.HORIZONTAL)
            setMenuResource(R.menu.nav_menu)
        }

    }


}
