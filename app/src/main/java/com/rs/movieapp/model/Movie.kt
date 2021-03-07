package com.rs.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.util.StringUtil
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

@Entity
data class Movie (
    @SerializedName("backdrop_path")
    val backgroundImage: String = "",
    @SerializedName("genre_ids")
    val genres: List<Int> = emptyList(),
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("original_name")
    val title: String = "",
    val overview: String = "",
    @SerializedName("poster_path")
    val posterImage: String = "",
    @SerializedName("first_air_date")
    val releaseDate: Date = Date(),
    var saved: Boolean = false
) {
    fun getYear(): Int {
        val calendar = Calendar.getInstance()
        calendar.time = releaseDate
        return calendar.get(Calendar.YEAR)
    }
}
