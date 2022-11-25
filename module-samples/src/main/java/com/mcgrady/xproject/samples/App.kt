package com.mcgrady.xproject.samples

import android.app.Application
import com.mcgrady.xproject.samples.lifecycle.ActivityLifecycleCallbacksImpl

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    }
}