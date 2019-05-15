package com.akashgarg.sample.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by Akash Garg on 14-05-2019.
 */
open class BaseActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    fun showToast(msg: String) {
        Toast.makeText(this@BaseActivity, msg, Toast.LENGTH_SHORT).show()
    }
}