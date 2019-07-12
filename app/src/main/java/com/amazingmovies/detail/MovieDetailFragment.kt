package com.amazingmovies.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amazingmovies.R
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.view.Initializer
import dagger.android.support.AndroidSupportInjection


class MovieDetailFragment : Fragment(), Initializer{

    private lateinit var args: MovieDetailFragmentArgs
    private lateinit var movieDetail: MovieInfo

    private val VIDEO_ID = "gOMhN-hfMtY"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun references() {
        args = MovieDetailFragmentArgs.fromBundle(arguments!!)
        movieDetail = args.movieDetail
    }

    override fun actions() {
    }

    override fun observables() {
    }


}
