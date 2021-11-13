package com.oguzdogdu.newsapp.presentation.fragments.searchfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.ListItemSearchBinding
import com.oguzdogdu.newsapp.domain.model.Article
import com.oguzdogdu.newsapp.presentation.fragments.searchfragment.SearchFragmentDirections

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(val binding: ListItemSearchBinding) : RecyclerView.ViewHolder(binding.root)


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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val newsList = news[position]

        holder.binding.apply {
            cardView.startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    R.anim.rv_animation
                )
            )
            textViewListItem.text = newsList.title
            imageListItem.load(newsList.urlToImage) {
                transformations(RoundedCornersTransformation(25f))
            }
        }
        holder.binding.root.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(newsList)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount() = news.size
}