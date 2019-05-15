package com.akashgarg.sample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

/**
 * @author Akash Garg on 15-05-2019.
 */

open class AppConstants {

    companion object {
        val LOADING = "Loading"
        val LOADED = "Loaded"
        val URL: String = "url"
        const val APP_NAME: String = "app_name"

        fun checkInternetConnection(context: Context): Boolean {
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (anInfo in info) {
                    if (anInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
            return false
        }

        @JvmStatic
        @BindingAdapter("bind:imageUrl")
        fun ImageView.loadImage(url: String) {
            Picasso.with(this.context)
                .load(url)
                .error(android.R.drawable.ic_menu_report_image).into(this)
        }
    }
}