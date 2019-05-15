package com.akashgarg.sample.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.akashgarg.sample.R
import com.akashgarg.sample.utils.AppConstants
import com.akashgarg.sample.utils.PermissionListener
import com.akashgarg.sample.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_start.*

/**
 * Created by Akash Garg on 14-05-2019.
 */

class StartActivity : BaseActivity(), PermissionListener {

    lateinit var permissionUtils: PermissionUtils

    val permissions = arrayOf(
        PermissionUtils.WRITE_EXTERNAL_STORAGE,
        PermissionUtils.READ_EXTERNAL_STORAGE

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    private fun getLayout(): Int {
        return R.layout.activity_start
    }

    override fun onStart() {
        super.onStart()
        permissionUtils = PermissionUtils().with(this@StartActivity).setListener(this)
    }

    override fun onResume() {
        super.onResume()
        btnStart.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 23) {
                for (permission in permissions) {
                    if (ContextCompat.checkSelfPermission(
                            this@StartActivity,
                            permission
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        permissionUtils.requestPermission(
                            arrayOf(
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        )
                    } else {
                        if (AppConstants.checkInternetConnection(this@StartActivity))
                            startActivity(Intent(this@StartActivity, MainActivity::class.java))
                        else
                            showToast(getString(R.string.no_internet))
                    }
                }
            } else {
                if (AppConstants.checkInternetConnection(this@StartActivity))
                    startActivity(Intent(this@StartActivity, MainActivity::class.java))
                else
                    showToast(getString(R.string.no_internet))
            }
        }
    }

    override fun allPermissionGranted() {
    }

    override fun onNeverAskAgainPermission(string: Array<String>) {
        permissionUtils.requestPermission(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    override fun onDenied(string: Array<String>) {
        permissionUtils.requestPermission(
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

}