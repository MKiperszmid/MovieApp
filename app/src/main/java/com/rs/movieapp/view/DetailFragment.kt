package com.rs.movieapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rs.movieapp.R
import com.rs.movieapp.model.Movie
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_list.*

class DetailFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            drawMovie(it)
        })

        fd_back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

    private fun drawMovie(movie: Movie) {
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/" + movie.posterImage).into(fd_poster)
        fd_title.text = movie.title
        fd_year.text = movie.getYear().toString()
        fd_subscribe.setOnClickListener {
            Toast.makeText(requireContext(), "Not yet implemented", Toast.LENGTH_SHORT).show()
        }
        fd_overview.text = movie.overview


    }
}
