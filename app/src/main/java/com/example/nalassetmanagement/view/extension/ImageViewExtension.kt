package com.example.nalassetmanagement.view.extension

import android.widget.ImageView
import com.example.nalassetmanagement.R
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(uri: String?, categoryId: Int?) {
    val holder = when (categoryId) {
        1 -> {
            R.drawable.ic_computer
        }

        2 -> {
            R.drawable.ic_phone
        }
        3 -> {
            R.drawable.ic_screen
        }
        4 -> R.drawable.ic_keybroad

        else -> {
            R.drawable.ic_computer
        }
    }
    if (uri.isNullOrEmpty()) {
        this.setImageResource(holder)
    } else {
        Picasso.get().load(uri).placeholder(holder).error(holder).into(this)
    }
}
