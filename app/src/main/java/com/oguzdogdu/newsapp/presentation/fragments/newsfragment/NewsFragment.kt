package com.oguzdogdu.newsapp.presentation.fragments.newsfragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.FragmentNewsBinding
import com.oguzdogdu.newsapp.presentation.fragments.newsfragment.adapter.NewsAdapter
import com.oguzdogdu.newsapp.util.Status.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private val viewModel: NewsViewModel by viewModels()

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
        sendData()
    }

    private fun setUpRv() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun sendData() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("newsArgs", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_detailFragment,
                bundle
            )
        }
    }

    private fun observeData() {
        viewModel.newsResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                SUCCESS -> {
                    hideProgressBar()
                    it.data.let { newsResponse ->
                        if (newsResponse != null) {
                            newsAdapter.news = newsResponse.articles
                        }
                    }
                }
                ERROR -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                LOADING -> {
                    showProgressBar()
                }
            }
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

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
