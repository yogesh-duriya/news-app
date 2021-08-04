package com.example.newsapplication.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.data.remote.response.LatestNewsResponse
import com.example.newsapplication.databinding.HomeNewsItemBinding


class NewsAdapter internal constructor(
    private val itemList: List<LatestNewsResponse.News>
) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.home_news_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(itemList[position])


    class ViewHolder(
        internal val binding: HomeNewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: LatestNewsResponse.News) {
            binding.homeNoticeData = news
            binding.executePendingBindings()
            binding.newsImage?.let {
                Glide.with(itemView.context)
                    .load(news.image_url)
                    .into(it)
            }
        }
    }
}