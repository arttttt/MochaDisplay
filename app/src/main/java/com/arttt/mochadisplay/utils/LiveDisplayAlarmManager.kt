package com.arttt.mochadisplay.utils

import android.app.AlarmManager
import android.content.Context
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import com.arttt.mochadisplay.LiveDisplayAlarmReceiver
import org.joda.time.DateTimeFieldType
import org.joda.time.DateTime
import org.joda.time.Interval

class LiveDisplayAlarmManager(private val mContext: Context) {
    fun createEventAndRegister() {
        removeEvent()
        val service = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mContext, LiveDisplayAlarmReceiver::class.java)
        intent.action = Constants.alarmAction

        var start = DateTime()

        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val end  = when (LiveDisplayTimeUtils.instance.getTimeType()) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            service.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, start.millis, pendingIntent)
        } else {
            service.set(AlarmManager.RTC_WAKEUP, start.millis, pendingIntent)
        }
        Log.d(Constants.TAG, "CREATED")
    }

    fun removeEvent() {
        val service = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mContext, LiveDisplayAlarmReceiver::class.java)
        intent.action = Constants.alarmAction
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        service.cancel(pendingIntent)
        Log.d(Constants.TAG, "DESTROYED")
    }
}