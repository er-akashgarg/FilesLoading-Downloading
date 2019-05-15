package com.akashgarg.sample.di

import com.akashgarg.sample.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Akash Garg on 14-05-2019.
 */

@Component(modules = [AppUtilsModules::class])
@Singleton
interface AppComponent {
    fun doInjectInApp(activity: MainActivity)
}