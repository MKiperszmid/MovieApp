package com.rs.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.rs.movieapp.networking.RetrofitClient
import com.rs.movieapp.networking.TmdbService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.getService().create(TmdbService::class.java)

        val response = liveData {
            emit(retrofit.getPopularMovies())
        }

        response.observe(this, Observer {
            val movies = it.body()?.movies ?: emptyList()
            if (movies.isNotEmpty()) {

            }
        })
    }
}