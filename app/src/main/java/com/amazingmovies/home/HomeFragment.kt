package com.amazingmovies.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.amazingmovies.R
import com.amazingmovies.core.extensions.hasConnection
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.view.ActivityInteraction
import com.amazingmovies.core.view.Argument
import com.amazingmovies.core.view.Initializer
import com.amazingmovies.home.adapter.PopularMovieAdapter
import com.amazingmovies.home.adapter.TopMovieAdapter
import com.amazingmovies.home.adapter.UpcomingMovieAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_home.recyclerViewPopular
import kotlinx.android.synthetic.main.fragment_home.recyclerViewRated
import kotlinx.android.synthetic.main.fragment_home.recyclerViewUpcoming
import kotlinx.android.synthetic.main.fragment_home.progressBarPopular
import kotlinx.android.synthetic.main.fragment_home.progressBarRated
import kotlinx.android.synthetic.main.fragment_home.progressBarUpcoming
import javax.inject.Inject

class HomeFragment : Fragment(), Initializer {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var activityInteraction: ActivityInteraction
    private lateinit var viewAdapterPopular: PopularMovieAdapter
    private lateinit var viewAdapterTop: TopMovieAdapter
    private lateinit var viewAdapterUpcoming: UpcomingMovieAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is ActivityInteraction)
            activityInteraction = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun references() {
        val hasConnection = context?.hasConnection ?: false
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
        Log.i("references", "$hasConnection")
        homeViewModel.popularMovies(hasConnection)
        homeViewModel.topRatedMovies(hasConnection)
        homeViewModel.upcomingMovies(hasConnection)

        activityInteraction.setTitle(R.string.wellcome)

        viewAdapterPopular = PopularMovieAdapter(activity!!.applicationContext)
        recyclerViewPopular.apply {
            setHasFixedSize(true)
            adapter = viewAdapterPopular
        }

        viewAdapterTop = TopMovieAdapter(activity!!.applicationContext)
        recyclerViewRated.apply {
            setHasFixedSize(true)
            adapter = viewAdapterTop
        }

        viewAdapterUpcoming = UpcomingMovieAdapter(activity!!.applicationContext)
        recyclerViewUpcoming.apply {
            setHasFixedSize(true)
            adapter = viewAdapterUpcoming
        }
    }

    override fun actions() {

        viewAdapterPopular.setOnItemClickListener(object : PopularMovieAdapter.OnItemClickListener {
            override fun onClickPopularMovie(view: View, movieInfo: MovieInfo) {
                Log.i("onClickPopularMovie", movieInfo.toString())
                goToDetail(movieInfo)
            }
        })

        viewAdapterTop.setOnItemClickListener(object : TopMovieAdapter.OnItemClickListener {
            override fun onClickTopMovie(view: View, movieInfo: MovieInfo) {
                Log.i("onClickTopMovie", movieInfo.toString())
                goToDetail(movieInfo)
            }
        })

        viewAdapterUpcoming.setOnItemClickListener(object :
            UpcomingMovieAdapter.OnItemClickListener {
            override fun onClickUpcomingMovie(view: View, movieInfo: MovieInfo) {
                Log.i("onClickUpcomingMovie", movieInfo.toString())
                goToDetail(movieInfo)
            }
        })

    }

    override fun observables() {

        homeViewModel.getPopularMovies().observe(this, Observer { popularMovies ->
            if (popularMovies?.results != null) {
                viewAdapterPopular.addPopularMovies(popularMovies.results as MutableList<MovieInfo>)
            } else {
                showDialogErrorMessage(getString(R.string.service_fail))
            }
        })

        homeViewModel.getTopRatedMovies().observe(this, Observer { topMovies ->
            if (topMovies?.results != null) {
                viewAdapterTop.addTopMovies(topMovies.results as MutableList<MovieInfo>)
            } else {
                showDialogErrorMessage(getString(R.string.service_fail))
            }
        })

        homeViewModel.getUpcomingMovies().observe(this, Observer { upcomingMovies ->
            if (upcomingMovies?.results != null) {
                viewAdapterUpcoming.addUpcomingMovies(upcomingMovies.results as MutableList<MovieInfo>)
            } else {
                showDialogErrorMessage(getString(R.string.service_fail))
            }
        })

        homeViewModel.loading().observe(this, Observer { loading ->
            if (loading != null) {
                showProgress(loading)
            }
        })

    }

    @SuppressLint("NewApi")
    private fun showDialogErrorMessage(message: String) {
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(R.string.accept) { dialog, _ ->
                dialog.dismiss()
                dialog.cancel()
                return@setPositiveButton
            }
            .create()
        dialogBuilder.show()
    }

    private fun showProgress(show: Boolean) {
        progressBarPopular.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        progressBarRated.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
        progressBarUpcoming.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }

        recyclerViewPopular.visibility = if (show) {
            View.GONE
        } else {
            View.VISIBLE
        }

        recyclerViewRated.visibility = if (show) {
            View.GONE
        } else {
            View.VISIBLE
        }

        recyclerViewUpcoming.visibility = if (show) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun goToDetail(detail: MovieInfo) {
        val args = Bundle()
        args.putParcelable(Argument.MOVIE_DETAIL, detail)
        findNavController().navigate(R.id.action_home_to_movieDetailFragment, args)
    }
}