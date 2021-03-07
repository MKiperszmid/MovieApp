package com.rs.movieapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rs.movieapp.dao.MovieRepository
import com.rs.movieapp.model.Genre
import com.rs.movieapp.model.Movie
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    companion object {
        val service = RetrofitClient.getService().create(TmdbService::class.java)
    }

    val movie = MutableLiveData<Movie>()
    val genreList = MutableLiveData<List<Genre>>()

    val savedMovies = repository.movies

    init {
        updateGenres()
    }

    private fun updateGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            genreList.postValue(service.getGenres().body()?.genres ?: emptyList())
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            repository.saveMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    fun isMovieInSaved(id: Int): Boolean = savedMovies.value?.firstOrNull { x -> x.id == id } != null

    val movies = liveData<List<Movie>>(Dispatchers.IO) {
        emit(service.getPopularMovies().body()?.movies ?: emptyList())
    }
}