package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.response.LatestNewsResponse
import retrofit2.http.GET

interface ApiServices {

    @GET("1/news?apikey=pub_716ec2dbc6daa9536bd2dad121e9c9419f9")
    suspend fun getLatestNews(): LatestNewsResponse

}