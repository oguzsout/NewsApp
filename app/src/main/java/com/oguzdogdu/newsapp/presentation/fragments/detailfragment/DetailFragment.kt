package com.oguzdogdu.newsapp.presentation.fragments.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        backStack()

        val news = args.newsArgs

        if (news != null) {
            binding.description.text = news.description
        }
        if (news != null) {
            binding.content.text = news.content
        }
        if (news != null) {
            binding.publish.text = news.publishedAt
        }
        if (news != null) {
            binding.imageListItem.load(news.urlToImage) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(25f))
            }

        }
        binding.linkButton.setOnClickListener {
            val action = news?.let { web ->
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}