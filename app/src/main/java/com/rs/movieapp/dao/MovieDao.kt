package com.rs.movieapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rs.movieapp.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE movie.saved = 1")
    fun selectAllLikedMovies() : LiveData<List<Movie>>
}