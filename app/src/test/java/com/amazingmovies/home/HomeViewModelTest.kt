package com.amazingmovies.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.amazingmovies.core.repository.Api
import com.amazingmovies.core.repository.models.GetMoviesResponse
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest{
    @Mock
    private lateinit var api: Api

    @Mock
    lateinit var mockGetMoviesResponse: GetMoviesResponse

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mutableLiveData = MutableLiveData<GetMoviesResponse>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockGetMoviesResponse = GetMoviesResponse(true)

    }

    @Test
    fun getAllMovies() {
        `when`(api.getMovies()).thenReturn(Observable.just(mockGetMoviesResponse))
        mutableLiveData.postValue(mockGetMoviesResponse)
        assertEquals(mockGetMoviesResponse, mutableLiveData.value)

    }
}