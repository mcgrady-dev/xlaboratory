package com.mcgrady.xproject.samples.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.mcgrady.xproject.samples.MainActivity
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.lifecycle.ActivityLifecycleCallbacksImpl

class FragmentSampleActivity : AppCompatActivity() {

    private lateinit var firstFragment: Fragment
    private lateinit var secondFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val btnAdd = findViewById<MaterialButton>(R.id.btn_add)
        val btnShow = findViewById<MaterialButton>(R.id.btn_show)
        val btnHide = findViewById<MaterialButton>(R.id.btn_hide)
        val btnReplace = findViewById<MaterialButton>(R.id.btn_replace)

        firstFragment = FirstFragment.newInstance()
        secondFragment = SecondFragment.newInstance()

        btnAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, firstFragment)
                .commitNow()
        }

        btnShow.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .show(firstFragment)
                .commitNow()
        }

        btnHide.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .hide(firstFragment)
                .commitNow()
        }

        btnReplace.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, secondFragment)
                .commitNow()
        }

//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//        }, 1000L)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(ActivityLifecycleCallbacksImpl.TAG, " ${this::class.simpleName}: onSaveInstanceState, outState=$outState")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.i(ActivityLifecycleCallbacksImpl.TAG, " ${this::class.simpleName}: onRestoreInstanceState, outState=$savedInstanceState")
    }
}