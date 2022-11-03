package com.dmitriy.instagramclone.app.utils.downloader

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.net.URL

@SuppressLint("StaticFieldLeak")
class PhotoUploader(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg urls: String?): Bitmap? {
        val imageUrl = urls[0]
        var image: Bitmap? = null
        try {
            val `in` = URL(imageUrl).openStream()
            image = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("logs", e.message.toString())
            e.printStackTrace()
        }
        return image
    }

    @Deprecated("Deprecated in Java", ReplaceWith("imageView.setImageBitmap(result)"))
    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }
}