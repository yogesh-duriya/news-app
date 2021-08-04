package com.example.newsapplication.utils

import android.app.Activity
import android.content.Intent
import androidx.databinding.BindingAdapter
import com.example.newsapplication.enableLogging
import java.lang.Exception

// # Kotlin Extensions

//- [View](#view)
//- [Context](#context)
//- [Fragment](#fragment)
//- [Activity](#activity)
//- [ViewGroup](#viewgroup)
//- [TextView](#textview)
//- [String](#string)
//- [Other](#other)


// ## View

fun Exception.safeLog() {
    if(enableLogging()) printStackTrace()
}

// ## Activity

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNewIntent(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

/*
@BindingAdapter("imageUrl")
fun loadImage(view: RoundedImageView, url: String) = Glide.with(view.context).load(url).into(view)*/
