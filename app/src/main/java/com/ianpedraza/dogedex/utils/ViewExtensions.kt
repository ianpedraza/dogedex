package com.ianpedraza.dogedex.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ianpedraza.dogedex.R

class ViewExtensions {
    companion object {
        fun ImageView.fromUrl(
            urlImage: String
        ) {
            Glide.with(this)
                .load(urlImage)
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_broken)
                .into(this)
        }

        fun View.hideView(hide: Boolean = true) {
            visibility = if (hide) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}
