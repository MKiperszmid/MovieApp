package com.rs.movieapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rs.movieapp.dao.MovieRepository
import com.rs.movieapp.model.Genre
import com.rs.movieapp.model.Movie
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    companion object {
        val service = RetrofitClient.getService().create(TmdbService::class.java)
    }

    val movie = MutableLiveData<Movie>()
    val genreList = MutableLiveData<List<Genre>>()
    val popularMovies = MutableLiveData<List<Movie>>()
    val loading = MutableLiveData<Boolean>()

    val savedMovies = repository.movies
    private var currentPage = 1
    private var hasContent = true

    init {
        updateGenres()
        getPopularMovies()
    }

    private fun updateGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            var result = emptyList<Genre>()
            try {
                result = service.getGenres().body()?.genres ?: emptyList()
            } catch (e: Exception) {
                //TODO: Error de Servicio
            }
            genreList.postValue(result)
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

    fun getPopularMovies() {
        if (hasContent) {
            viewModelScope.launch(Dispatchers.IO) {
                loading.postValue(true)
                var latestMovies = emptyList<Movie>()
                try {
                    val movieList = service.getPopularMovies(currentPage++).body()
                    if (movieList != null) {
                        latestMovies = movieList.movies
                        hasContent = movieList.totalPages >= currentPage
                    }
                } catch (e: Exception) {
                    //TODO: Error de Servicio
                }
                popularMovies.postValue(latestMovies)
                loading.postValue(false)
            }
        }
    }

    fun isMovieInSaved(id: Int): Boolean =
        savedMovies.value?.firstOrNull { x -> x.id == id } != null
}