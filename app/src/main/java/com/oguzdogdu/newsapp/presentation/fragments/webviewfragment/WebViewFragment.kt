package com.oguzdogdu.newsapp.presentation.fragments.webviewfragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oguzdogdu.newsapp.databinding.FragmentWebViewBinding
import com.oguzdogdu.newsapp.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewSetup()
        backStackWeb()
    }

    private fun backStackWeb() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun webViewSetup() {
        binding.webview.webViewClient = WebViewClient()

        binding.webview.apply {
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            loadUrl(args.webArgs.url)
        }
    }
}