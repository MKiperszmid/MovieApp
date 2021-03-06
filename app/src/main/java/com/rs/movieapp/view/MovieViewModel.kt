package com.rs.movieapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rs.movieapp.model.Genre
import com.rs.movieapp.model.Movie
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    companion object {
        val service = RetrofitClient.getService().create(TmdbService::class.java)
    }

    val movie = MutableLiveData<Movie>()
    val genreList = MutableLiveData<List<Genre>>()

    init {
        updateGenres()
    }

    private fun updateGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genreList.postValue(service.getGenres().body()?.genres ?: emptyList())
        }
    }

    val movies = liveData<List<Movie>>(Dispatchers.IO) {
        emit(service.getPopularMovies().body()?.movies ?: emptyList())
    }
}