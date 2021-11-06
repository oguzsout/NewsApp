package com.oguzdogdu.newsapp.ui.fragments.newsfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.oguzdogdu.newsapp.databinding.ListItemBinding
import com.oguzdogdu.newsapp.model.Article
import com.oguzdogdu.newsapp.ui.fragments.newsfragment.NewsFragmentDirections

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    class NewsHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var news: List<Article>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val newsList = news[position]

        holder.binding.apply {
            textViewListItem.text = newsList.title
            imageListItem.load(newsList.urlToImage) {
                crossfade(true)
                crossfade(1000)
            }
        }
        holder.binding.root.setOnClickListener {
            val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(newsList)
            findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = news.size
}
