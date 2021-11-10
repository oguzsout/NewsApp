package com.oguzdogdu.newsapp.presentation

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.oguzdogdu.newsapp.databinding.ActivityMainBinding
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
    }
}