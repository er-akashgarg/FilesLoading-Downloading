package com.akashgarg.sample.utils

import android.app.ActivityManager
import android.content.Context


/**
 * @author Akash Garg on 15-05-2019.
 */

object AppUtils {

    fun isMyServiceRunning(serviceClass: Class<*>, context: Context): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}