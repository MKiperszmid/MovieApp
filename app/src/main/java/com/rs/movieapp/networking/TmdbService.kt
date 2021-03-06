package com.rs.movieapp.networking

import com.rs.movieapp.model.GenreList
import com.rs.movieapp.model.MovieList
import retrofit2.Response
import retrofit2.http.GET

interface TmdbService {
    @GET("tv/popular")
    suspend fun getPopularMovies(): Response<MovieList>

    @GET("genre/tv/list")
    suspend fun getGenres(): Response<GenreList>
}