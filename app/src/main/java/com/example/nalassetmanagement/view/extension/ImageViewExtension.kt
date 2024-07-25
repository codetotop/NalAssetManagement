package com.example.nalassetmanagement.view.extension

import android.widget.ImageView
import com.example.nalassetmanagement.R
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(uri: String?) {
    if (uri.isNullOrEmpty()) {
        this.setImageResource(R.drawable.ic_computer)
    } else {
        Picasso.get().load(uri).placeholder(R.drawable.ic_computer).error(R.drawable.ic_computer).into(this)
    }
}
