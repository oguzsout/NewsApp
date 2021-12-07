package com.oguzdogdu.newsapp.presentation.fragments.newsfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oguzdogdu.newsapp.R
import com.oguzdogdu.newsapp.databinding.ListItemBinding
import com.oguzdogdu.newsapp.domain.model.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>(){

    inner class NewsHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                textViewListItem.text = article.title
                val imageLink = article.urlToImage
                imageListItem.load(imageLink) {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    private var onItemClickListener: ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {

        val newsList = news[position]
        holder.bind(newsList)

        holder.binding.apply {
            cardView.startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    R.anim.rv_animation
                )
            )
        }
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                it(newsList)
            }
           /* val action = NewsFragmentDirections.actionNewsFragmentToDetailFragment(newsList)
            findNavController(it).navigate(action)

            */
        }
    }
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount() = news.size
}
