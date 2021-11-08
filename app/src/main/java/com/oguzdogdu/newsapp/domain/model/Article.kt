package com.oguzdogdu.newsapp.domain.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
):Parcelable