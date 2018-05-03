package com.arttt.mochadisplay

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.arttt.mochadisplay.utils.*

class LiveDisplayAlarmReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            with(context.getSharedPreferences(Constants.prefsTitle, Context.MODE_PRIVATE)) {
                if(getInt(Constants.prefsLiveDisplayEnabled, 0) == 0)
                    return
            }
        }

        SU.instance.getSuAccessAsync(object : SU.OnSuAccessListener {
            override fun onSuAccess(rooted: Boolean) {
                if (rooted) {
                    val colorManager = ColorManager(context, SU.instance)
                    val liveDisplayAlarmManager = LiveDisplayAlarmManager(context)

                    colorManager.setTemperature(colorManager.getColor(LiveDisplayTimeUtils.instance.getTimeType()))
                    liveDisplayAlarmManager.createEventAndRegister()

                    SU.instance.close()
                }
            }
        })
    }
}