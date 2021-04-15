package com.amazingmovies.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.amazingmovies.R
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.view.ActivityInteraction
import com.amazingmovies.core.view.Initializer
import com.amazingmovies.search.adapter.SearchAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.recyclerViewSearch
import kotlinx.android.synthetic.main.fragment_search.progressBarSearch
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import com.amazingmovies.core.extensions.hasConnection
import com.amazingmovies.core.extensions.rxSearch
import com.amazingmovies.core.view.Argument
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(), Initializer {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var activityInteraction: ActivityInteraction
    private lateinit var viewSearch: SearchAdapter
    private lateinit var searchView: SearchView
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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

    @SuppressLint("CheckResult")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = getText(R.string.search_genre)
        searchView.rxSearch
            .debounce(300,TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNullOrEmpty()){
                    viewSearch.clearMovies()
                } else {
                    if(context?.hasConnection == true){
                        searchViewModel.findMovies(it)
                    }
                }
            }
    }


    override fun references() {
        activityInteraction.setTitle(R.string.search_fragment)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]

        viewSearch = SearchAdapter(requireActivity().applicationContext)
        recyclerViewSearch.apply {
            setHasFixedSize(true)
            adapter = viewSearch
        }

    }

    override fun actions() {

        viewSearch.setOnItemClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onClickFindMovie(view: View, movieInfo: MovieInfo) {
                goToDetail(movieInfo)
            }
        })

    }

    override fun observables() {

        searchViewModel.getFindMovies().observe(this, Observer { findMovies ->
            if (findMovies?.results != null){
                viewSearch.addFindMovies(findMovies.results as MutableList<MovieInfo>)
            }
        })

        searchViewModel.loading().observe(this, Observer { loading ->
            if (loading != null) {
                showProgress(loading)
            }
        })

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

    private fun goToDetail(detail: MovieInfo) {
        val args = Bundle()
        args.putParcelable(Argument.MOVIE_DETAIL, detail)
        findNavController().navigate(R.id.action_search_to_movieDetailFragment, args)

    }
}