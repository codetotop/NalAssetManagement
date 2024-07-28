package com.example.nalassetmanagement.view.extension

import android.widget.ImageView
import com.example.nalassetmanagement.R
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(uri: String?, categoryId: Int?) {
    val holder = when (categoryId) {
        1 -> {
            R.drawable.mackbook
        }

        2 -> {
            R.drawable.iphone
        }

        3 -> {
            R.drawable.monitor
        }

        4 -> R.drawable.phone_xiao

        else -> {
            R.drawable.mackbook
        }
    }
    if (uri.isNullOrEmpty()) {
        this.setImageResource(holder)
    } else {
        Picasso.get().load(uri).placeholder(holder).error(holder).into(this)
    }
}
