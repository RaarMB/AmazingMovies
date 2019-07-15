package com.amazingmovies.core.repository.models

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMoviesResponseTest{

    private lateinit var getMoviesResponse: GetMoviesResponse

    @Before
    fun setup() {
        getMoviesResponse = GetMoviesResponse(true)
    }

    @Test
    fun testGenerateTitle() {
        val title = "Spider-Man: Lejos de Casa"
        Assert.assertEquals(title, getMoviesResponse.results!!.first().title)
    }

}