package com.oguzdogdu.newsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<Article>,
    val totalResults: Int
):Parcelable

