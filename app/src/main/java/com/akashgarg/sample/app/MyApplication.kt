package com.akashgarg.sample.app

import android.app.Application
import android.content.Context
import com.akashgarg.sample.di.AppComponent
import com.akashgarg.sample.di.AppUtilsModules
import com.akashgarg.sample.di.DaggerAppComponent

/**
 * Created by Akash Garg on 14-05-2019.
 */
open class MyApplication : Application() {

    private var appComponent: AppComponent? = null
    private var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        mContext = this
        appComponent = DaggerAppComponent.builder().appUtilsModules(AppUtilsModules()).build()
    }

    public fun getAppComponent(): AppComponent {
        return appComponent!!
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}