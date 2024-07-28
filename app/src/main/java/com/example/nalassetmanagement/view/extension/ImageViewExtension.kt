package com.example.nalassetmanagement.view.extension

import android.widget.ImageView
import com.example.nalassetmanagement.R
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(uri: String?, categoryId: Int?) {
    val holder = when (categoryId) {
        1 -> {
            R.drawable.laptop
        }

        2 -> {
            R.drawable.phone
        }
        3 -> {
            R.drawable.ic_screen
        }
        4 -> R.drawable.ic_keybroad

        else -> {
            R.drawable.monitor
        }
    }
    if (uri.isNullOrEmpty()) {
        this.setImageResource(holder)
    } else {
        Picasso.get().load(uri).placeholder(holder).error(holder).into(this)
    }
}
