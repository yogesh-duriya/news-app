package com.example.newsapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.remote.response.LatestNewsResponse
import com.example.newsapplication.data.repository.NewsRepository
import com.example.newsapplication.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: NewsRepository
) : BaseViewModel() {

    val otpResponse = MutableLiveData<LatestNewsResponse>()

    fun getNews() {
        viewModelScope.launch {
            otpResponse.value = userRepository.getLatestNews()

        }
    }


}