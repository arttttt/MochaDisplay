package com.arttt.mochadisplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.arttt.mochadisplay.utils.*

class LiveDisplayAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        SU.instance.getSuAccessSync()
        val colorManager = ColorManager(SU.instance)
        val liveDisplayAlarmManager = LiveDisplayAlarmManager(context, colorManager)
        val temperature = when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                colorManager.getColor(context, LiveDisplayTimeUtils.instance.getDayType())
            }
            Constants.alarmAction -> {
                intent.getIntExtra(Constants.intentExtraTemperature,
                        Constants.defaultColorTemperature)
            }
            else -> Constants.defaultColorTemperature
        }

        colorManager.setTemperature(temperature)
        liveDisplayAlarmManager.createEventAndRegister()
        SU.instance.close()
    }
}