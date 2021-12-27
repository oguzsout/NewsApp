package com.oguzdogdu.newsapp.presentation.fragments.searchfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.ListItemSearchBinding
import com.oguzdogdu.newsapp.domain.model.Article

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
    private var onItemClickListener: ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val newsList = news[position]

        holder.binding.apply {
            cardSearch.startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    R.anim.rv_animation
                )
            )
            txtSearchTitle.text = newsList.title
            imgSearch.load(newsList.urlToImage) {
                transformations(RoundedCornersTransformation(25f))
            }
        }
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                it(newsList)
            }
           /* val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(newsList)
            Navigation.findNavController(it).navigate(action)

            */
        }
    }
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount() = news.size
}