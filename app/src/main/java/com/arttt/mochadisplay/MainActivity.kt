package com.arttt.mochadisplay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arttt.mochadisplay.fragments.LiveDisplayFragment
import com.arttt.mochadisplay.utils.LiveDisplayAlarmManager
import com.arttt.mochadisplay.utils.SU

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, LiveDisplayFragment.newInstance())
                .commit()
    }
}
