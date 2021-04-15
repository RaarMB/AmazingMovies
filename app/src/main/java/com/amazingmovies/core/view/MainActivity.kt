package com.amazingmovies.core.view

import android.os.Bundle
import com.amazingmovies.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideActionBar()
        hideStatusBar()
    }

    override fun disableBottomNavigation() {}

    override fun enableBottomNavigation() {}

    override fun hideBottomNavigation() {}

    override fun showBottomNavigation() {}

    override fun showSpinner() {}

    override fun hideSpinner() {}
}
