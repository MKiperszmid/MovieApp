package com.rs.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.movieapp.R
import com.rs.movieapp.model.Movie
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]

        viewModel.movies.observe(requireActivity(), Observer {
            if (it.isEmpty()) {
                //No hay peliculas
            } else {
                fl_movies_recycler.apply {
                    adapter = PopularMovieAdapter(it)
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                }
            }
        })
    }
}