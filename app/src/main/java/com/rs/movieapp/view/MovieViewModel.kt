package com.rs.movieapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rs.movieapp.model.Movie
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService
import kotlinx.coroutines.Dispatchers

class MovieViewModel : ViewModel() {
    companion object {
        val service = RetrofitClient.getService().create(TmdbService::class.java)
    }

    val movie = MutableLiveData<Movie>()

    val movies = liveData<List<Movie>>(Dispatchers.IO) {
        emit(service.getPopularMovies().body()?.movies ?: emptyList())
    }
}