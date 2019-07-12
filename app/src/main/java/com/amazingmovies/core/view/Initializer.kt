package com.amazingmovies.core.view

interface Initializer {

    fun init(){
        references()
        observables()
        actions()
    }

    fun references()
    fun actions()
    fun observables()

}