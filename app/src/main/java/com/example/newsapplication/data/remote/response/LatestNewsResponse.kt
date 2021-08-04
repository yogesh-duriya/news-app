package com.example.newsapplication.data.remote.response

data class LatestNewsResponse(
    val status: String,
    val totalResults: String,
    val results: List<News>,
){

    data class News(
        val title: String,
        val link: String,
        val description: String,
        val pubDate: String,
        val source_id: String,
        val image_url: String?
    )


}