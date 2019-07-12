package com.amazingmovies.core.view

interface ActivityInteraction {
    fun showErrorMessage(message: String)
    fun setTitle(titleResId: Int)
    fun disableBottomNavigation()
    fun enableBottomNavigation()
    fun hideBottomNavigation()
    fun showBottomNavigation()
    fun showActionBar()
    fun hideActionBar()
    fun showSpinner()
    fun hideSpinner()
    fun disableView()
    fun enableView()
    fun showStatusBar()
    fun hideStatusBar()
}