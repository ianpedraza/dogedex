package com.ianpedraza.dogedex.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import coil.load
import com.ianpedraza.dogedex.R
import java.io.File

class ViewExtensions {
    companion object {
        fun AndroidViewModel.getString(@StringRes resId: Int): String {
            return getApplication<Application>().getString(resId)
        }

        fun Context.showToast(@StringRes message: Int) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        fun Context.showToast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

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

        fun ImageView.fromFile(
            file: File
        ) {
            this.load(file) {
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

        fun Activity.createOutputDirectory(name: String): File {
            val mediaDir = externalMediaDirs.firstOrNull()?.let { file ->
                File(file, name).apply {
                    mkdirs()
                }
            }

            return if (mediaDir != null && mediaDir.exists()) {
                mediaDir
            } else {
                filesDir
            }
        }
    }
}
