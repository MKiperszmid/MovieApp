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

class SavedMovieAdapter(
    private val movieList: List<Movie>,
    private val callback: PopularMovieAdapter.MovieCallback
) :
    RecyclerView.Adapter<SavedMovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val items = layoutInflater.inflate(R.layout.savedmovie_item, parent, false)
        return PopularMovieViewHolder(items, callback)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class PopularMovieViewHolder(
        itemView: View,
        private val callback: PopularMovieAdapter.MovieCallback
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            PicassoUtil.getImage(movie.posterImage)
                .into(itemView.findViewById<ImageView>(R.id.pmi_background))
            itemView.setOnClickListener {
                callback.movieClicked(movie)
            }
        }
    }
}
