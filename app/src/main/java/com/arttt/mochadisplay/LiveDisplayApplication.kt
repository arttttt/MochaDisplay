package com.arttt.mochadisplay

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class LiveDisplayApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
    }
}