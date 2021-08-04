package com.example.newsapplication.data.repository

import com.example.newsapplication.data.remote.response.LatestNewsResponse

interface NewsRepository {

    suspend fun getLatestNews(): LatestNewsResponse

}