package com.oguzdogdu.newsapp.presentation.fragments.sportsfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.SportsRowBinding
import com.oguzdogdu.newsapp.domain.model.Article
import com.oguzdogdu.newsapp.presentation.fragments.newsfragment.NewsFragmentDirections

class SportsFragAdapter : RecyclerView.Adapter<SportsFragAdapter.SportViewHolder>() {
    inner class SportViewHolder(val binding: SportsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                cardNews.startAnimation(
                    android.view.animation.AnimationUtils.loadAnimation(
                        binding.root.context,
                        R.anim.rv_animation
                    )
                )
                txtTitle.text = article.title
                val imageLink = article.urlToImage
                imgNews.load(imageLink) {
                    crossfade(true)
                    crossfade(1000)
                    transformations(RoundedCornersTransformation(25f))
                }
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var news: List<Article>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportViewHolder {
        return SportViewHolder(SportsRowBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: SportViewHolder, position: Int) {
        val newsList = news[position]
        holder.bind(newsList)

        holder.binding.root.setOnClickListener { view ->
            val action = SportsFragmentDirections.actionSportsFragmentToDetailFragment(newsList)
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun getItemCount() = news.size
}