package com.mcgrady.xproject.samples.view

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mcgrady.xproject.samples.R

class CustomViewActivity : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var customView: CustomView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        //addButtonToWindow()

        //checkOpenGLVersion()

        checkClickListener()
    }

    private fun checkClickListener() {
        val customViewGroup  =findViewById<CustomViewGroup>(R.id.custom_view_group)
        customViewGroup.setOnClickListener {
            Log.d(INPUT_TAG, "CustomViewGroup onClick")
        }
    }

    private fun addButtonToWindow() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        btn = Button(this.baseContext)
        btn.text = "Click me"

        val lp = WindowManager.LayoutParams().apply {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            }
            gravity = Gravity.CENTER
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.or(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        }

        wm.addView(btn, lp)

        btn.setOnClickListener {
            wm.removeView(btn)
        }
    }

    private fun checkOpenGLVersion() {
        val activityManager: ActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val configInfo: ConfigurationInfo = activityManager.deviceConfigurationInfo

        println(configInfo.glEsVersion.toDouble())
        println(configInfo.reqGlEsVersion >= 0x30000)
        println(String.format("%X", configInfo.reqGlEsVersion))
    }

    override fun onResume() {
        super.onResume()

        //testViewPost()
    }

    private fun testViewPost() {
        customView = findViewById(R.id.custom_view)

        Log.e("getWidth", "${customView.width}")
        Handler(Looper.getMainLooper()).post {
            //Log.e("getHeight", "${customView.width}")
            Toast.makeText(this, "getWidth=${customView.width}", Toast.LENGTH_LONG).show()
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(INPUT_TAG, "$TAG dispatchTouchEvent event=${getAction(ev?.action)} return super")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(INPUT_TAG, "$TAG onTouchEvent event=${getAction(event?.action)} return super")
        return super.onTouchEvent(event)
    }

    companion object {
        const val INPUT_TAG = "InputEventDispatch"
        const val TAG = "CustomViewActivity"

        fun getAction(action: Int?) = when(action) {
            0 -> "ACTION_DOWN"
            1 -> "ACTION_UP"
            2 -> "ACTION_MOVE"
            3 -> "ACTION_CANCEL"
            else -> "null"
        }
    }
}