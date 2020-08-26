package com.example.prutc.peershare.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.prutc.peershare.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, parent: ViewGroup? = this) {
    LayoutInflater.from(context).inflate(layoutRes, parent)
}

fun View.inflate(@LayoutRes layoutRes: Int, parent: ViewGroup? = null) {
    LayoutInflater.from(context).inflate(layoutRes, parent)
}

fun ImageView.showProfileCircle(imageUrl: String) {
    val defaultImage = R.drawable.ic_launch
    val requestOptions = RequestOptions
        .placeholderOf(defaultImage)
        .error(defaultImage)
        .circleCrop()

    Glide.with(this)
        .setDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .into(this)
}