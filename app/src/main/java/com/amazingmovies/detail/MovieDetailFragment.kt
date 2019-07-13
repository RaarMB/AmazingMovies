package com.amazingmovies.detail

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.amazingmovies.R
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.view.Initializer
import dagger.android.support.AndroidSupportInjection
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.amazingmovies.core.configuration.constants.VideoIdentifier
import com.amazingmovies.core.view.BaseActivity
import com.amazingmovies.databinding.FragmentMovieDetailBinding
import com.amazingmovies.detail.models.MovieDetail
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : Fragment(), Initializer{

    private lateinit var args: MovieDetailFragmentArgs
    private lateinit var movieDetail: MovieDetail
    private lateinit var baseActivity: BaseActivity
    private lateinit var binding: FragmentMovieDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        binding = DataBindingUtil.bind<FragmentMovieDetailBinding>(view)!!
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        binding.detail = movieDetail

    }

    override fun references() {
        args = MovieDetailFragmentArgs.fromBundle(arguments!!)
        movieDetail = MovieDetail(
            id = args.movieDetail.id!!,
            popularity = args.movieDetail.popularity!!.toString(),
            release_date = args.movieDetail.release_date!!,
            title = args.movieDetail.title!!,
            overview = args.movieDetail.overview!!,
            language = args.movieDetail.original_language!!
        )
        baseActivity = activity as BaseActivity
        baseActivity.title = movieDetail.title
    }

    override fun actions() {
    }

    override fun observables() {

        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                val videoId = when(movieDetail.id){
                    429617 -> VideoIdentifier.TRAILER_SP_FAR_FROM_HOME
                    399579 -> VideoIdentifier.TRAILER_ALITA
                    458156 -> VideoIdentifier.TRAILER_JOHNWICK
                    301528 -> VideoIdentifier.TRAILER_TOY_STORY
                    420818 -> VideoIdentifier.TRAILER_LION_KING
                    456740 -> VideoIdentifier.TRAILER_HELLBOY
                    287947 -> VideoIdentifier.TRAILER_SHAZAM
                    299537 -> VideoIdentifier.TRAILER_CAPTIAN_MARVEL
                    521029 -> VideoIdentifier.TRAILER_ANNABELLE
                    537915 -> VideoIdentifier.TRAILER_AFTER
                    511987 -> VideoIdentifier.TRAILER_CRAWL
                    920 -> VideoIdentifier.TRAILER_CARS
                    299536 -> VideoIdentifier.TRAILER_INFINITY_WAR
                    566555 -> VideoIdentifier.TRAILER_CONAN
                    299534 -> VideoIdentifier.TRAILER_END_GAME
                    420817 -> VideoIdentifier.TRAILER_ALADDIN
                    553100 -> VideoIdentifier.TRAILER_WILD_AND_FREE
                    329996 -> VideoIdentifier.TRAILER_DUMBO
                    486131 -> VideoIdentifier.TRAILER_SHAFT
                    else -> VideoIdentifier.TRAILER_DEFAULT

                }
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }


}
