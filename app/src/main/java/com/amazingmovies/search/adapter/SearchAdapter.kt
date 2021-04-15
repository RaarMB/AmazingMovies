package com.amazingmovies.search.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amazingmovies.BuildConfig
import com.amazingmovies.R
import com.amazingmovies.core.repository.models.MovieInfo
import com.amazingmovies.core.utils.ImageUrl
import com.amazingmovies.databinding.CardMovieGenreBinding
import com.amazingmovies.search.models.MovieFind

class SearchAdapter(var context: Context) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var listener: OnItemClickListener
    private var movieList: MutableList<MovieInfo> = ArrayList()

    class SearchViewHolder(val binding: CardMovieGenreBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        setOnItemClickListener(listener)
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: CardMovieGenreBinding =
            DataBindingUtil.inflate(inflater, R.layout.card_movie_genre, parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val imageView = holder.binding.imagePoster
        if (movieList[position].poster_path.isNullOrEmpty() ||
            movieList[position].poster_path?.contains("null") == true) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.ic_upcoming))
        } else {
            ImageUrl(imageView).execute("${BuildConfig.SERVER_URL_IMAGES}${movieList[position].poster_path}")
        }
        Log.d("onbindviewholder", "Element $position set.")
        holder.binding.movieFind =
            MovieFind(movieList[position].title.orEmpty(), movieList[position].poster_path.orEmpty())
        holder.binding.root.setOnClickListener {
            listener.onClickFindMovie(it, movieList[position])
        }
    }

    override fun getItemCount() = movieList.size

    interface OnItemClickListener {
        fun onClickFindMovie(view: View, movieInfo: MovieInfo)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun addFindMovies(addMovieList: MutableList<MovieInfo>) {
        movieList.clear()
        movieList.addAll(addMovieList)
        notifyDataSetChanged()
    }

    fun clearMovies() {
        movieList.clear()
    }

}