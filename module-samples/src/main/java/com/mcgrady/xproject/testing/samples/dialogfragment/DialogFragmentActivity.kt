package com.mcgrady.xproject.testing.samples.dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.mcgrady.xproject.testing.samples.R

class DialogFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_fragment)
    }

    override fun onStart() {
        super.onStart()

//        Handler(Looper.getMainLooper()).postDelayed({
            FirstDialogFragment().showNow(supportFragmentManager, "FirstDialogFragment")
//        }, 3000)
    }
}