package com.rs.movieapp.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.rs.movieapp.model.Movie
import com.rs.movieapp.util.PicassoUtil
import com.rs.movieapp.utils.BitmapUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import jp.wasabeef.picasso.transformations.GrayscaleTransformation
import kotlinx.android.synthetic.main.fragment_detail.*


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
        viewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            drawMovie(it)
        })

        fd_back.setOnClickListener {
            requireFragmentManager().popBackStack()
        }
    }

    private fun loadPoster(posterUrl: String) {
        PicassoUtil.getImage(posterUrl)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    //
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    //
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    bitmap?.let {
                        fd_poster.setImageBitmap(BitmapUtils.toGrayscale(it))
                        getPalette(it)
                    }
                }
            })
    }

    private fun loadBackground(backgroundUrl: String) {
        PicassoUtil.getImage(backgroundUrl).transform(GrayscaleTransformation())
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    //
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    //
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    fd_background.background = BitmapDrawable(resources, bitmap)
                }
            })
    }

    private fun drawMovie(movie: Movie) {
        loadPoster(movie.posterImage)
        loadBackground(movie.backgroundImage)

        fd_title.text = movie.title
        fd_year.text = movie.getYear().toString()
        fd_subscribe.setOnClickListener {
            Toast.makeText(requireContext(), "Not yet implemented", Toast.LENGTH_SHORT).show()
        }
        fd_overview.apply {
            text = movie.overview
            movementMethod = ScrollingMovementMethod()
        }
    }

    private fun getPalette(bitmap: Bitmap) {
        val colors = Palette.from(bitmap).generate()
        colors.dominantSwatch?.let { swatch ->
            //TODO: Set filter for background and poster images
            //fd_background.setBackgroundColor(swatch.rgb)
        }
    }
}
