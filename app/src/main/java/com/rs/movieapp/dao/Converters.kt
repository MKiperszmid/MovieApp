package com.rs.movieapp.dao

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromList(genreList: List<Int>): String {
        var result = ""
        for (genre in genreList) {
            result += ("$genre,")
        }
        return result
    }

    @TypeConverter
    fun toList (genre: String): List<Int> {
        val listado = genre.split(',') as MutableList
        listado.removeAt(listado.size - 1)
        val result = mutableListOf<Int>()
        for (item in listado) {
            result.add(item.toInt())
        }
        return result
    }

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate (milis: Long): Date = Date(milis)
}