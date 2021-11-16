package com.oguzdogdu.newsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.oguzdogdu.newsapp.MainCoroutineRule
import com.oguzdogdu.newsapp.getOrAwaitValueTest
import com.oguzdogdu.newsapp.presentation.fragments.searchfragment.SearchViewModel
import com.oguzdogdu.newsapp.repo.FakeNewsRepository
import com.oguzdogdu.newsapp.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

        @get:Rule
        var instantTaskExecutorRule = InstantTaskExecutorRule()

        @get:Rule
        var mainCoroutineRule = MainCoroutineRule()

        private lateinit var viewModel: SearchViewModel

        @Before
        fun setup() {
            viewModel = SearchViewModel(FakeNewsRepository())
        }

        @Test
        fun `data success`() {
            viewModel.searchNews("Türkiye")
            val value = viewModel.newsResponse.getOrAwaitValueTest()
            Truth.assertThat(value.status).isEqualTo(Status.SUCCESS)
        }
    }
