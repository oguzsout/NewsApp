package com.oguzdogdu.newsapp.ui.fragments.detailfragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    private val args :  DetailFragmentArgs by navArgs()

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
        val news = args.newsArgs

        if (news != null) {
            binding.title.text = news.title
        }
        if (news != null) {
            binding.description.text = news.description
        }
        if (news != null) {
            binding.content.text = news.content
        }
        if (news != null) {
            binding.imageListItem.load(news.urlToImage) {
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(10f))
            }
        }
        binding.linkButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            if (news != null) {
                openURL.data = Uri.parse(news.url)
            }
            startActivity(openURL)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}