package com.amazingmovies.dashboard

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.amazingmovies.R
import com.amazingmovies.core.broadcasts.ConnectionReceiver
import com.amazingmovies.core.view.BaseActivity
import kotlinx.android.synthetic.main.activity_dashboard.bottomNavigation
import kotlinx.android.synthetic.main.activity_dashboard.progressDashboard

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupBottomNavMenu(findNavController(R.id.nav_host_dashboard))
        registerReceiver(ConnectionReceiver(), IntentFilter(CONNECTIVITY_CHANGE))
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav?.setupWithNavController(navController)
    }

    override fun disableBottomNavigation() {}

    override fun enableBottomNavigation() {}

    override fun hideBottomNavigation() {
        bottomNavigation.visibility = View.GONE
    }

    override fun showBottomNavigation() {
        bottomNavigation.visibility = View.VISIBLE
    }

    override fun showSpinner() {
        progressDashboard.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        progressDashboard.visibility = View.GONE
    }

    private companion object {
        const val CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE"
    }

}