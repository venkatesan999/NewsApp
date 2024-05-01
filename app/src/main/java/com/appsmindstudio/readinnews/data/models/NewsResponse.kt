package com.appsmindstudio.readinnews.data.models

data class NewsResponse(
    val status: String? = null,
    val totalResults: String? = null,
    val articles: List<Article> = emptyList()
)

data class Article(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    val source: Source? = null
)

data class Source(
    val id: String? = null,
    val name: String? = null
)
