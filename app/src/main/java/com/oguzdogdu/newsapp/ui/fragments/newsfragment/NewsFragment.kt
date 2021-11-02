package com.oguzdogdu.newsapp.ui.fragments.newsfragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.FragmentNewsBinding
import com.oguzdogdu.newsapp.ui.fragments.newsfragment.adapter.NewsAdapter
import com.oguzdogdu.newsapp.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        observeData()
        swipeRefreshData()
    }

    private fun setUpRv() {
        newsAdapter = NewsAdapter()
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeData() {
        viewModel.newsResponse.observe(viewLifecycleOwner, {
            newsAdapter.news = it.articles
        })
    }

    private fun swipeRefreshData() {
        binding.swipeContainer.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.swipeContainer.setColorSchemeColors(Color.RED)

        binding.swipeContainer.setOnRefreshListener {
            observeData()
            Toast.makeText(requireContext(), "Refresh From API", Toast.LENGTH_SHORT).show()
            binding.swipeContainer.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
