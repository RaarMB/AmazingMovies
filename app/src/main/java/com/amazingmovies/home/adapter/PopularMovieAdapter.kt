package com.amazingmovies.home.adapter

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
import com.amazingmovies.databinding.CardMovieBinding

class PopularMovieAdapter(var context: Context) :
    RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder>() {
    private lateinit var listener: OnItemClickListener
    private var movieList: MutableList<MovieInfo> = ArrayList()
    class MovieViewHolder(public val binding: CardMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        setOnItemClickListener(listener)
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: CardMovieBinding = DataBindingUtil.inflate(inflater, R.layout.card_movie, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val imageView = holder.binding.imageMovie
        ImageUrl(imageView).execute("${BuildConfig.SERVER_URL_IMAGES}${movieList[position].poster_path}")
        Log.d("onbindviewholder", "Element $position set.")
        holder.binding.movie = movieList[position]
        holder.binding.root.setOnClickListener {
            listener.onClickPopularMovie(it, movieList[position])
        }
    }

    override fun getItemCount() = movieList.size

    interface OnItemClickListener {
        fun onClickPopularMovie(view: View, movieInfo: MovieInfo)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    fun addPopularMovies(addMovieList: MutableList<MovieInfo>){
        movieList.clear()
        movieList.addAll(addMovieList)
        notifyDataSetChanged()
    }

}