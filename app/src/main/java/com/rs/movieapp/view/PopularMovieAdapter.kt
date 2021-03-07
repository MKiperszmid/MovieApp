package com.rs.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rs.movieapp.R
import com.rs.movieapp.model.Genre
import com.rs.movieapp.model.Movie
import com.rs.movieapp.util.PicassoUtil
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class PopularMovieAdapter(
    private val movieList: MutableList<Movie>,
    private val callback: MovieCallback,
    private val viewModel: MovieViewModel
) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val items = layoutInflater.inflate(R.layout.popularmovie_item, parent, false)
        return PopularMovieViewHolder(items, callback, viewModel)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun addMovies(movies: List<Movie>) {
        movieList.addAll(movies)
        notifyDataSetChanged()
    }

    class PopularMovieViewHolder(
        itemView: View,
        private val callback: MovieCallback,
        private val viewModel: MovieViewModel
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            PicassoUtil.getImage(movie.backgroundImage)
                .transform(GrayscaleTransformation())
                .into(itemView.findViewById<ImageView>(R.id.pmi_background))
            itemView.findViewById<TextView>(R.id.pmi_title).text = movie.title
            findGenreName(movie.genres)
            itemView.setOnClickListener {
                callback.movieClicked(movie)
            }
        }

        private fun findGenreName(genresId: List<Int>) {
            if (genresId.isEmpty()) return

            for (genre in viewModel.genreList.value ?: emptyList()) {
                if (genre.id == genresId[0]) {
                    itemView.findViewById<TextView>(R.id.pmi_genre).apply {
                        visibility = View.VISIBLE
                        text = genre.name
                    }
                }
            }
        }
    }

    interface MovieCallback {
        fun movieClicked(movie: Movie)
    }
}
