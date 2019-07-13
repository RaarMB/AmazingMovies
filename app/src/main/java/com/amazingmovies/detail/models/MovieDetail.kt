package com.amazingmovies.detail.models

data class MovieDetail(
    val id: Int,
    val popularity: String,
    val release_date: String,
    val title: String,
    val overview: String,
    val language: String
)