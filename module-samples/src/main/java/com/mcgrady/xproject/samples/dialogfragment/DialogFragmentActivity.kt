package com.mcgrady.xproject.samples.dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mcgrady.xproject.samples.R

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