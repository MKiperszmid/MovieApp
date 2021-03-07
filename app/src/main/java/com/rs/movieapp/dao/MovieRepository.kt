package com.rs.movieapp.dao

import com.rs.movieapp.model.Movie

class MovieRepository(private val movieDao: MovieDao) {
    val movies = movieDao.selectAllLikedMovies()
    suspend fun saveMovie(movie: Movie) {
        movieDao.saveMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}