package com.rs.movieapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Movie (
    @SerializedName("backdrop_path")
    val backgroundImage: String,
    @SerializedName("genre_ids")
    val genres: List<Int>,
    val id: Int,
    @SerializedName("original_name")
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterImage: String,
    @SerializedName("first_air_date")
    private val releaseDate: Date
) {
    fun getYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = releaseDate
        return calendar.get(Calendar.YEAR)
    }
}
