package com.arttt.mochadisplay.utils

import android.app.AlarmManager
import android.content.Context
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.arttt.mochadisplay.LiveDisplayAlarmReceiver
import org.joda.time.DateTimeFieldType
import org.joda.time.DateTime
import org.joda.time.Interval

class LiveDisplayAlarmManager(private val mContext: Context, private val mColorManager: ColorManager) {
    fun createEventAndRegister() {
        val service = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mContext, LiveDisplayAlarmReceiver::class.java)
        intent.action = Constants.alarmAction

        var start = DateTime()
        val dayTime = LiveDisplayTimeUtils.instance.getDayType()
        val temperature = when (dayTime) {
            LiveDisplayTimeUtils.TimeType.DAY -> mColorManager.getColor(mContext, LiveDisplayTimeUtils.TimeType.DAY)
            else -> mColorManager.getColor(mContext, LiveDisplayTimeUtils.TimeType.NIGHT)
        }

        intent.putExtra(Constants.intentExtraTemperature, temperature)
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        var end  = when (dayTime) {
            LiveDisplayTimeUtils.TimeType.DAY -> {
                DateTime(start.get(DateTimeFieldType.year()),
                        start.get(DateTimeFieldType.monthOfYear()),
                        start.get(DateTimeFieldType.dayOfMonth()),
                        20,
                        0,
                        0)
            }
            else -> {
                val time = DateTime(start.get(DateTimeFieldType.year()),
                        start.get(DateTimeFieldType.monthOfYear()),
                        start.get(DateTimeFieldType.dayOfMonth()),
                        8,
                        0,
                        0)
                time.plusDays(1)
            }
        }

        val interval = Interval(start, end)
        start = start.plus(interval.toDurationMillis())

        service.set(AlarmManager.RTC_WAKEUP, start.millis, pendingIntent)
        Log.d(Constants.TAG, "CREATED")
    }

    fun cancelJob() {
        val service = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mContext, LiveDisplayAlarmReceiver::class.java)
        intent.action = Constants.alarmAction
        var pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        service.cancel(pendingIntent)
        Log.d(Constants.TAG, "DESTROYED")
    }
}