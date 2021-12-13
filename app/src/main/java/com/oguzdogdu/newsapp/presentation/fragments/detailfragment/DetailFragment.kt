package com.oguzdogdu.newsapp.presentation.fragments.detailfragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.databinding.FragmentDetailBinding
import com.oguzdogdu.newsapp.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backStack()
        bindIncomingData()
    }

    private fun bindIncomingData() {

        val article = args.newsArgs

        if (article != null) {
            binding.description.text = article.description
        }
        if (article != null) {
            binding.content.text = article.content
        }
        if (article != null) {
            binding.publish.text = article.publishedAt
        }
        if (article != null) {
            binding.imageListItem.load(article.urlToImage) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(25f))
            }

        }
        binding.linkButton.setOnClickListener {
            val action = article?.let { web ->
                DetailFragmentDirections.actionDetailFragmentToWebViewFragment(
                    web
                )
            }
            if (action != null) {
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun backStack() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }
}