package com.amazingmovies.core.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import android.graphics.BitmapFactory


class ImageUrl constructor(@field:SuppressLint("StaticFieldLeak") var imageView: ImageView): AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg params: String?): Bitmap {

        val imageURL = params[0]
        var bimage: Bitmap? = null
        try {
            val input = java.net.URL(imageURL).openStream()
            bimage = BitmapFactory.decodeStream(input)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bimage!!
    }

    override fun onPostExecute(result: Bitmap) {
        imageView.setImageBitmap(result)
    }
}