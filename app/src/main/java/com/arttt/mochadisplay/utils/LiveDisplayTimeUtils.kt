package com.arttt.mochadisplay.utils

import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType

class LiveDisplayTimeUtils {

    companion object {
        val instance by lazy {
            LiveDisplayTimeUtils()
        }
    }

    enum class TimeType {
        DAY, NIGHT
    }

    fun getDayType(): TimeType {
        return if (DateTime().get(DateTimeFieldType.hourOfDay()) in 9..19)
            TimeType.DAY
        else
            TimeType.NIGHT
    }
}