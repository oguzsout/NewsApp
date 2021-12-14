package com.oguzdogdu.newsapp.presentation.fragments.searchfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.FragmentSearchBinding
import com.oguzdogdu.newsapp.presentation.fragments.base.BaseFragment
import com.oguzdogdu.newsapp.presentation.fragments.searchfragment.adapter.SearchAdapter
import com.oguzdogdu.newsapp.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var searchAdapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        searching()
        observeSearchData()
        backStack()

        searchAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("newsArgs", it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_detailFragment,
                bundle
            )
        }

    }

    private fun searching() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews(query!!)
                showProgressBar()
                binding.searchRv.visibility = View.VISIBLE
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setUpRv() {
        binding.searchRv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            hideProgressBar()
        }
    }

    private fun observeSearchData() {
        viewModel.newsResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    it.data.let { newsResponse ->
                        if (newsResponse != null) {
                            searchAdapter.news = newsResponse.articles
                        }
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Log.e("TAG", "An error occured: $message")
                    }
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun backStack() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun hideProgressBar() {
        binding.progressBarSearch.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarSearch.visibility = View.VISIBLE
    }
}

