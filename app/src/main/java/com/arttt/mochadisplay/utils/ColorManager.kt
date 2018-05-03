package com.arttt.mochadisplay.utils

import android.content.Context

class ColorManager(private val mContext: Context, private val mSU: SU) {
    private val sRedFilter = "sys/class/graphics/fb0/device/color_filter_krr"
    private val sGreenFilter = "sys/class/graphics/fb0/device/color_filter_kgg"
    private val sBlueFilter = "sys/class/graphics/fb0/device/color_filter_kbb"

    fun setTemperature(temperature: Int) {
        val color = ColorUtils.instance.getRGBFromK(temperature)
        with(mSU) {
            writeFile(sRedFilter, (color.red * 255 + 0.5).toInt().toString(16))
            writeFile(sGreenFilter, (color.green * 255 + 0.5).toInt().toString(16))
            writeFile(sBlueFilter, (color.blue * 255 + 0.5).toInt().toString(16))
        }
    }

    fun saveColor(temperature: Int, colorType: LiveDisplayTimeUtils.TimeType) {
        val prefs = mContext.getSharedPreferences(Constants.prefsTitle, Context.MODE_PRIVATE)
        val type = when (colorType) {
            LiveDisplayTimeUtils.TimeType.DAY -> Constants.prefsDayTemperatureKey
            else -> Constants.prefsNightTemperatureKey
        }
        prefs.edit()
                .putInt(type, temperature)
                .apply()
    }

    fun getColor(colorType: LiveDisplayTimeUtils.TimeType): Int {
        val prefs = mContext.getSharedPreferences(Constants.prefsTitle, Context.MODE_PRIVATE)
        val type = when (colorType) {
            LiveDisplayTimeUtils.TimeType.DAY -> Constants.prefsDayTemperatureKey
            else -> Constants.prefsNightTemperatureKey
        }

        return prefs.getInt(type, Constants.defaultColorTemperature)
    }
}