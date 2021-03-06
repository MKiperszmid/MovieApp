package com.rs.movieapp.util

import com.rs.movieapp.BuildConfig
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

object PicassoUtil {
    fun getImage(path: String): RequestCreator = Picasso.get().load(BuildConfig.IMAGE_HOST + path)
}