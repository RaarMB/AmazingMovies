package com.amazingmovies.core.view

import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.amazingmovies.R

abstract class BaseActivity: AppCompatActivity(), ActivityInteraction {

    private val touchDispatcherRunnable = Runnable { enableView() }
    private val touchDispatcherHandler = Handler()
    private var blockTouchEvents = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return !blockTouchEvents && super.dispatchTouchEvent(ev)
    }

    override fun showActionBar() {
        supportActionBar?.show()
    }

    override fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun disableView() {
        blockTouchEvents = true
        touchDispatcherHandler.removeCallbacks(touchDispatcherRunnable)
    }

    override fun enableView() {
        blockTouchEvents = false
    }

    override fun showErrorMessage(message: String) {

        runOnUiThread {
            val dialogBuilder = AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(R.string.accept) { dialog, _ ->
                    dialog.dismiss()
                    dialog.cancel()
                    return@setPositiveButton
                }
                .create()
            dialogBuilder.show()
        }

    }

    override fun hideStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun showStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

}