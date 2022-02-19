package com.oguzdogdu.newsapp.presentation.fragments.sportsfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzdogdu.newsapp.databinding.FragmentSportsBinding
import com.oguzdogdu.newsapp.presentation.fragments.base.BaseFragment
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportsFragment : BaseFragment<FragmentSportsBinding>(FragmentSportsBinding::inflate) {
    private val sportAdapter: SportsFragAdapter by lazy { SportsFragAdapter() }
    private val viewModel: SportsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeData()
    }

    private fun setUpRecyclerView() {
        binding.rvSports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sportAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            setHasFixedSize(true)
        }
    }

    private fun observeData() {
        viewModel.newsResponse.observe(viewLifecycleOwner, { result ->

            when (result) {
                is Resource.Success -> {
                    result.data?.articles.also {
                        if (it != null) {
                            sportAdapter.news = it
                        }
                    }
                }
                is Resource.Error -> {
                    result.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
    }
}