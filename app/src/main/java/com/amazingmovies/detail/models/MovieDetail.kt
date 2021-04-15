package com.amazingmovies.detail.models

data class MovieDetail(
    val id: Int,
    val popularity: String,
    val releaseDate: String,
    val title: String,
    val overview: String,
    val language: String
)