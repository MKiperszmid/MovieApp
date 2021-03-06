package com.rs.movieapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie (
    @SerializedName("backdrop_path")
    val backgroundImage: String,
    @SerializedName("genre_ids")
    val genres: List<Int>,
    val id: Int,
    @SerializedName("original_title")
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterImage: String,
    @SerializedName("release_date")
    private val releaseDate: Date
)
