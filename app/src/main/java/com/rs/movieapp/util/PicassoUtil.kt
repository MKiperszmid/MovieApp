package com.rs.movieapp.util

import com.rs.movieapp.BuildConfig
import com.rs.movieapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

object PicassoUtil {
    fun getImage(path: String?): RequestCreator {
        if (path == null) {
            return Picasso.get().load(R.drawable.tvplaceholder)
        }
        return Picasso.get().load(BuildConfig.IMAGE_HOST + path)
    }
}