package com.arttt.mochadisplay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arttt.mochadisplay.fragments.LiveDisplayFragment
import com.arttt.mochadisplay.utils.LiveDisplayAlarmManager
import com.arttt.mochadisplay.utils.SU
import kotlinx.coroutines.experimental.launch

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SU.instance.getSuAccessAsync(object : SU.OnSuAccessListener {
            override fun onSuAccess(rooted: Boolean) {
                if (rooted) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.container, LiveDisplayFragment.newInstance())
                            .commit()
                }
            }
        })
    }
}
