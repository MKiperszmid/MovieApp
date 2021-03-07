package com.rs.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ListFragment : Fragment(), PopularMovieAdapter.MovieCallback {
    private lateinit var viewModel: MovieViewModel
    private var isLoading = true

    companion object {
        const val ITEMS_BEFORE_REQUESTING_MORE = 5
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = MovieDatabase.instance(requireActivity().application).movieDao
        val repository = MovieRepository(dao)
        val factory = MovieViewmodelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            var newVisibility = View.GONE
            var newIndeterminate = false
            if (it) {
                newVisibility = View.VISIBLE
                newIndeterminate = true
            }
            fl_progress.apply {
                visibility = newVisibility
                isIndeterminate = newIndeterminate
            }
        })

        drawPopularMovies()
        drawSavedMovies()

    }

    private fun getPopularMoviesAdapter(): PopularMovieAdapter {
        val popularMovieAdapter = PopularMovieAdapter(
            mutableListOf(),
            this@ListFragment,
            viewModel
        )
        popularMovieAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        return popularMovieAdapter
    }

    private fun drawPopularMovies() {
        val popularMovieAdapter = getPopularMoviesAdapter()
        fl_movies_recycler.apply {
            adapter = popularMovieAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        fl_movies_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!isLoading) {
                    val manager = recyclerView.layoutManager as LinearLayoutManager
                    val initPos = manager.findLastVisibleItemPosition()
                    val finalPos = manager.itemCount
                    if (finalPos - initPos <= ITEMS_BEFORE_REQUESTING_MORE) {
                        viewModel.getPopularMovies()
                        isLoading = true
                    }
                }
            }
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer {
            isLoading = false
            if (it.isEmpty()) {
                //TODO: No hay peliculas, hubo un error. Mostrar un mensaje de error
            } else {
                popularMovieAdapter.addMovies(it)
                fl_movies_container.visibility = View.VISIBLE
            }
        })
    }

    private fun drawSavedMovies() {
        viewModel.savedMovies.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                fl_saved_container.visibility = View.VISIBLE
                val saveMovieAdapter = SavedMovieAdapter(it, this@ListFragment)
                saveMovieAdapter.stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                fl_saved_recycler.apply {
                    adapter = saveMovieAdapter
                    layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                }
            } else {
                fl_saved_container.visibility = View.GONE
            }
        })
    }

    override fun movieClicked(movie: Movie) {
        viewModel.movie.value = movie
        requireFragmentManager().beginTransaction()
            .replace(R.id.am_fragment_container, DetailFragment()).addToBackStack(null).commit()
    }
}