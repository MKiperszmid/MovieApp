package com.rs.movieapp.view

import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.rs.movieapp.R
import com.rs.movieapp.dao.MovieDatabase
import com.rs.movieapp.dao.MovieRepository
import com.rs.movieapp.factory.MovieViewmodelFactory
import com.rs.movieapp.model.Movie
import com.rs.movieapp.util.PicassoUtil
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.fragment_detail_motion.*


class DetailFragment : Fragment() {
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_motion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = MovieDatabase.instance(requireActivity().application).movieDao
        val repository = MovieRepository(dao)
        val factory = MovieViewmodelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory)[MovieViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            drawMovie(it)
        })

        fd_back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

    private fun loadPoster(posterUrl: String) {
        PicassoUtil.getImage(posterUrl).placeholder(R.drawable.tvplaceholder).into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                //
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                //
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {

                    //fd_poster.setImageBitmap(BitmapUtils.toGrayscale(it))
                    getPalette(it)
                }
            }
        })
    }

    private fun drawMovie(movie: Movie) {
        loadPoster(movie.posterImage)
        PicassoUtil.getImage(movie.posterImage).placeholder(R.drawable.tvplaceholder)
            .transform(GrayscaleTransformation()).into(fd_poster)

        PicassoUtil.getImage(movie.backgroundImage).placeholder(R.drawable.tvplaceholder)
            .transform(GrayscaleTransformation()).into(fd_background)

        fd_title.text = movie.title
        fd_year.text = movie.getYear().toString()
        fd_subscribe.setOnClickListener {
            val newMovie = movie.copy()
            newMovie.saved = true
            viewModel.saveMovie(newMovie)
        }
        fd_overview.apply {
            text = movie.overview
            movementMethod = ScrollingMovementMethod()
        }
    }

    private fun getPalette(bitmap: Bitmap) {
        Palette.from(bitmap).generate {
            it?.dominantSwatch?.let { swatch ->
                fd_background.setColorFilter(swatch.rgb, PorterDuff.Mode.LIGHTEN)
                fd_poster.setColorFilter(swatch.rgb, PorterDuff.Mode.OVERLAY)
            }
        }
    }
}
