package com.mcgrady.xproject.samples.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.mcgrady.xproject.samples.R
import com.mcgrady.xproject.samples.fragment.ui.setting.SettingsFragment

class SampleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                val fragment = SettingsFragment()
                replace(R.id.container, fragment)
                setReorderingAllowed(true)
            }

//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, SampleFragment.newInstance())
//                .setReorderingAllowed(true)
//                .commitNow()
        }
    }
}