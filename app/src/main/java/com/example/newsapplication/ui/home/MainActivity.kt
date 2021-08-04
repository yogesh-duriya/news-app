package com.example.newsapplication.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, HomeViewModel>() {

    override val layoutId: Int = R.layout.activity_main

    override val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNews()

        viewModel.otpResponse.observe(this, {
            Toast.makeText(this, it.status, Toast.LENGTH_SHORT).show()


            viewBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            viewBinding.recyclerView.adapter = NewsAdapter(it.results)

        })

    }
}