package com.example.newsapplication.data.repository.impl

import com.example.newsapplication.data.remote.ApiServices
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.data.remote.response.LatestNewsResponse
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiServices
): NewsRepository {
    override suspend fun getLatestNews(): LatestNewsResponse {
        return apiService.getLatestNews()
    }
}