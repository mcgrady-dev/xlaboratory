/*
 * Copyright 2022 mcgrady
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mcgrady.xproject.samples.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

/**
 * Created by mcgrady on 2021/10/27.
 */
open class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks, IActivityLifecycle {

    companion object {

        const val TAG = "ActivityLifecycle"
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)

        registerFragmentCallbacks(activity)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(TAG, " ${activity::class.simpleName}: onCreated, taskId=${activity.taskId}")

//        statusBar(activity = activity as FragmentActivity) {
//            transparent()
//        }
//        @Suppress("USELESS_CAST")
//        navigationBar(activity = activity as FragmentActivity) {
//            transparent()
//        }
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName}: onStarted")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName}: onResumed")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName}: onPaused")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName}: onStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        Log.i(TAG, " ${activity::class.simpleName}: onSaveInstanceState, outState=$outState")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName}: onDestroyed")

        unregisterFragmentCallbacks(activity)
    }

    override fun registerFragmentCallbacks(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName} registerFragmentLifecycleCallbacks")
        (activity as FragmentActivity).supportFragmentManager.registerFragmentLifecycleCallbacks(
            FragmentLifecycleCallbacksImpl, true)
    }

    override fun unregisterFragmentCallbacks(activity: Activity) {
        Log.i(TAG, " ${activity::class.simpleName} unregisterFragmentLifecycleCallbacks")
        (activity as FragmentActivity).supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            FragmentLifecycleCallbacksImpl
        )
    }
}