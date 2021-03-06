package com.rs.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rs.movieapp.R
import com.rs.movieapp.model.Movie
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class PopularMovieAdapter(val movieList: List<Movie>) :
    RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val items = layoutInflater.inflate(R.layout.popularmovie_item, parent, false)
        return PopularMovieViewHolder(items)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie.backgroundImage)
                .transform(GrayscaleTransformation()).into(itemView.findViewById<ImageView>(R.id.pmi_background))
            itemView.findViewById<TextView>(R.id.pmi_title).text = movie.title
            itemView.findViewById<TextView>(R.id.pmi_genre).text = "Placeholder"
        }
    }
}
