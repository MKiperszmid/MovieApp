package com.rs.movieapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.movieapp.model.Movie
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    companion object {
        val service = RetrofitClient.getService().create(TmdbService::class.java)
    }

    val movies = MutableLiveData<List<Movie>>()

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movies.postValue(service.getPopularMovies().body()?.movies ?: emptyList())
        }
    }
}