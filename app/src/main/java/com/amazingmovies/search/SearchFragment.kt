package com.amazingmovies.search

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.SearchView
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
import androidx.core.view.MenuItemCompat
import androidx.core.view.MenuItemCompat.getActionView
import com.amazingmovies.core.extensions.hasConection
import com.amazingmovies.core.extensions.rxSearch
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
    private var genre: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
                }else{
                    if(context!!.hasConection){
                        searchViewModel.findMovies(it)
                    }else{

                    }
                }
            }
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

    }

    override fun observables() {

        searchViewModel.getFindMovies().observe(this, Observer { findMovies ->
            if (findMovies?.results != null){
                viewSearch.addFindMovies(findMovies.results as MutableList<MovieInfo>)
            }else{
                showDialogErrorMessage(getString(R.string.service_fail))
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
