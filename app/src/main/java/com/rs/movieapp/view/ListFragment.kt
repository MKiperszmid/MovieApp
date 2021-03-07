package com.rs.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.movieapp.R
import com.rs.movieapp.dao.MovieDatabase
import com.rs.movieapp.dao.MovieRepository
import com.rs.movieapp.factory.MovieViewmodelFactory
import com.rs.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class ListFragment : Fragment(), PopularMovieAdapter.MovieCallback {
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = MovieDatabase.instance(requireActivity().application).movieDao
        val repository = MovieRepository(dao)
        val factory = MovieViewmodelFactory(repository)

        viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                //TODO: No hay peliculas o hubo un error. Mostrar un mensaje de error
            } else {
                val movieAdapter = PopularMovieAdapter(it, this@ListFragment, viewModel.genreList.value ?: emptyList())
                movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                fl_movies_recycler.apply {
                    adapter = movieAdapter
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                }
            }
        })

        viewModel.savedMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                //TODO: Mostrar series guardadas
            }
        })


    }

    override fun movieClicked(movie: Movie) {
        viewModel.movie.value = movie
        requireFragmentManager().beginTransaction().replace(R.id.am_fragment_container, DetailFragment()).addToBackStack(null).commit()
    }
}