package com.ianpedraza.dogedex.utils

import android.view.View
import android.widget.ImageView
import coil.load
import com.ianpedraza.dogedex.R

class ViewExtensions {
    companion object {
        fun ImageView.fromUrl(
            urlImage: String
        ) {
            /*
            Glide.with(this)
                .load(urlImage)
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_broken)
                .into(this)
            */

            this.load(urlImage) {
                placeholder(R.drawable.ic_image_placeholder)
                error(R.drawable.ic_image_broken)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }

        fun View.showView(show: Boolean = true) {
            visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}
