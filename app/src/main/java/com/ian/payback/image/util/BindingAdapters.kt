package com.ian.payback.image.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ian.payback.image.R
import com.squareup.picasso.Picasso

object BindingAdapters {
    @BindingAdapter("fullimageUrl")
    @JvmStatic
    fun loadFullImage(imageView: ImageView, imageUrl: String) {
        if (imageUrl.isEmpty()) {
            imageView.setImageResource(R.drawable.placeholder);
        } else{
            Picasso.get().load(imageUrl).placeholder(R.drawable.placeholder).into(imageView)
        }

    }
}