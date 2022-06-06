package com.mcgrady.xproject.testing.samples

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import kotlin.concurrent.thread

class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        Log.d("is ART JVM ${Utils.isArtJvm()}")
//
//        Log.d("""
//            max memory = ${Runtime.getRuntime().maxMemory()}
//            total memory = ${Runtime.getRuntime().totalMemory()}
//            free memory = ${Runtime.getRuntime().freeMemory()}
//            current used memory = ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}
//
//        """.trimIndent())

        thread {
            Thread.sleep(3000)
        }
    }

    override fun getSharedPreferences(name: String?, mode: Int): SharedPreferences {
        return super.getSharedPreferences(name, mode)
    }
}