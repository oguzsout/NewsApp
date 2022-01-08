package com.oguzdogdu.newsapp.presentation.fragments.newsfragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.FragmentNewsBinding
import com.oguzdogdu.newsapp.presentation.fragments.base.BaseFragment
import com.oguzdogdu.newsapp.presentation.fragments.newsfragment.adapter.NewsAdapter
import com.oguzdogdu.newsapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

   private var newsAdapter = NewsAdapter()

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeData()
        swipeRefreshData()
    }

    private fun setUpRecyclerView() {
        binding.recyclerviewList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            setHasFixedSize(true)
        }
    }
    private fun observeData() {
        viewModel.newsResponse.observe(viewLifecycleOwner, { result ->

            when (result) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    result.data?.articles.also {
                        if (it != null) {
                            newsAdapter.news = it
                        }
                    }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    result.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun swipeRefreshData() {
        binding.swipe.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.swipe.setColorSchemeColors(Color.RED)

        binding.swipe.setOnRefreshListener {
            observeData()
            Toast.makeText(requireContext(), "Refresh From API", Toast.LENGTH_SHORT).show()
            binding.swipe.isRefreshing = false
        }
    }

    private fun showShimmerEffect() {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        binding.recyclerviewList.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
        binding.recyclerviewList.visibility = View.VISIBLE
    }
}
