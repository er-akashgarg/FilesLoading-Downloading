package com.akashgarg.sample.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

/**
 * @author Akash Garg on 15-05-2019.
 */

class PermissionUtils {

    companion object {
        var WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        var READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        var REQUEST_CODE = 10
    }

    private val permission = ArrayList<String>(0)
    private var mContext: Context? = null
    private var mListener: PermissionListener? = null
    private var deniedPermission = false


    fun with(context: Context): PermissionUtils {
        this.mContext = context
        return this
    }

    fun setListener(mListener: PermissionListener): PermissionUtils {
        this.mListener = mListener
        return this
    }

    private fun isAvailableAllPermission(): Boolean {
        var isAllowed = true
        for (item in permission) {
            isAllowed = ActivityCompat.checkSelfPermission(mContext!!, item) == PackageManager.PERMISSION_GRANTED
            if (!isAllowed)
                break
        }
        return isAllowed
    }

    fun requestPermission(permissions: Array<String>) {
        if (mListener == null)
            return
        if (mContext == null)
            return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mListener!!.allPermissionGranted()
            return
        }
        if (permissions.isNotEmpty()) {
            this.permission.clear()
            checkPermissionAlReadyGranted(permissions)
        }
        if (permission.size > 0) {
            if (mContext is Activity)
                ActivityCompat.requestPermissions(
                    mContext as Activity,
                    this.permission.toArray(arrayOfNulls(0)),
                    REQUEST_CODE
                )
        } else mListener!!.allPermissionGranted()
    }

    private fun checkPermissionAlReadyGranted(permission: Array<String>) {
        for (item in permission) {
            if (ActivityCompat.checkSelfPermission(mContext!!, item) != PackageManager.PERMISSION_GRANTED) {
                this.permission.addAll(permission)
            }
        }
    }

    fun onPermissionRequestResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (!isAvailableAllPermission()) {
                    for (name in permissions) {
                        deniedPermission =
                            ActivityCompat.shouldShowRequestPermissionRationale(mContext as Activity, name)
                        if (deniedPermission)
                            break
                    }
                    if (!deniedPermission) {
                        if (mListener != null) {
                            mListener!!.onNeverAskAgainPermission(permissions)
                        }
                    } else {
                        if (mListener != null) {
                            mListener!!.onDenied(permissions)
                        }
                    }
                } else if (mListener != null)
                    mListener!!.allPermissionGranted()
            }
        }
    }
}

interface PermissionListener {
    fun allPermissionGranted()

    fun onNeverAskAgainPermission(string: Array<String>)

    fun onDenied(string: Array<String>)
}