package com.example.moviesdb.utility

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesdb.R


/**
 * Created by PATINIK-CONT on 04-Oct-19.
 */

@BindingAdapter("imageUrl")
fun setImage(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_movies_placeholder)
                    .error(R.drawable.ic_movies_placeholder)
            )
            .load(url)
            .into(view)
    }
}