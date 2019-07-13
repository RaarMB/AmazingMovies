package com.amazingmovies.search

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
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

import com.amazingmovies.R
import com.amazingmovies.core.configuration.constants.NameGenres
import com.amazingmovies.core.extensions.addOnTextChangedListeners
import com.amazingmovies.core.extensions.value
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.view.ActivityInteraction
import com.amazingmovies.core.view.Initializer
import com.amazingmovies.search.adapter.SearchAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : Fragment(), Initializer {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var activityInteraction: ActivityInteraction
    private lateinit var viewSearch: SearchAdapter
    private lateinit var searchViewModel: SearchViewModel
    private var genre: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
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
        activityInteraction.setTitle(R.string.search_fragment)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]

        viewSearch = SearchAdapter(activity!!.applicationContext)
        recyclerViewSearch.apply {
            setHasFixedSize(true)
            adapter = viewSearch
        }


    }

    override fun actions() {

        viewSearch.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onClickFindMovie(view: View, movieInfo: MovieInfo) {
                Log.i("onClickPopularMovie", movieInfo.toString())
            }
        })

        searchMovie.addOnTextChangedListeners(onTextChanged = { s, _, _, _ ->
            if(s.isNullOrEmpty()){
                goneRecyclerViewSearch(true)
                viewSearch.clearMovies()
            }else{
                searchViewModel.genres()
            }
        })

    }

    override fun observables() {

        searchViewModel.getGenresMovies().observe(this, Observer { genresResponse ->
            if (genresResponse != null){
                when {
                    genresResponse.genres != null -> {
                        genresResponse.genres.forEach {
                            if(searchMovie.value.contains("accion", true)){
                                if(it.name == NameGenres.ACTION){
                                    genre = it.id
                                    searchViewModel.findMovies(genre!!)
                                    Log.i("findMovies", "esta buscando")
                                }
                            }else{
                                viewSearch.clearMovies()
                            }

                        }
                    }
                    true -> showDialogErrorMessage(getString(R.string.service_fail))
                }
            }
        })

        searchViewModel.getFindMovies().observe(this, Observer { findMovies ->
            if (findMovies != null){
                when {
                    findMovies.results != null -> {
                        viewSearch.addFindMovies(findMovies.results as MutableList<MovieInfo>)
                        Log.i("upcomingMovies", findMovies.toString())
                    }
                    true -> showDialogErrorMessage(getString(R.string.service_fail))
                }
            }
        })

        searchViewModel.loading().observe(this, Observer { loading ->
            if (loading != null) {
                showProgress(loading)
            }
        })

    }

    @SuppressLint("NewApi")
    private fun showDialogErrorMessage(message: String) {
        val dialogBuilder = AlertDialog.Builder(activity!!)
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
        progressBarSearch.visibility = if (show){
            View.VISIBLE
        }else {
            View.GONE
        }

        recyclerViewSearch.visibility = if (show){
            View.GONE
        }else {
            View.VISIBLE
        }
    }

    private fun goneRecyclerViewSearch(gone: Boolean) {
        recyclerViewSearch.visibility = if (gone){
            View.GONE
        }else {
            View.VISIBLE
        }
    }
}
