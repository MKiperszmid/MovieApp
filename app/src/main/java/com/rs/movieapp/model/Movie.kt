package com.rs.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Movie(
    @SerializedName("backdrop_path")
    private val _backgroundImage: String? = "",
    @SerializedName("genre_ids")
    private val _genres: List<Int>? = emptyList(),
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("original_name")
    private val _title: String? = "",
    @SerializedName("overview")
    private val _overview: String = "",
    @SerializedName("poster_path")
    private val _posterImage: String = "",
    @SerializedName("first_air_date")
    private val _releaseDate: Date = Date(),
    var saved: Boolean = false
) {
    val backgroundImage
        get() = _backgroundImage ?: ""

    val genres
        get() = _genres ?: emptyList()

    val title
        get() = _title ?: ""

    val overview
        get() = _overview ?: ""

    val posterImage
        get() = _posterImage ?: ""

    val releaseDate
        get() = _releaseDate ?: Date()


    fun getYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = releaseDate
        return calendar.get(Calendar.YEAR)
    }
}
