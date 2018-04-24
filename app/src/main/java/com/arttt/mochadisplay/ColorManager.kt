package com.arttt.mochadisplay

import android.content.SharedPreferences
import com.arttt.mochadisplay.utils.ImageUtils

class ColorManager(private val mSU: SU, private val mPrefs: SharedPreferences) {
    private val sRedFilter = "sys/class/graphics/fb0/device/color_filter_krr"
    private val sGreenFilter = "sys/class/graphics/fb0/device/color_filter_kgg"
    private val sBlueFilter = "sys/class/graphics/fb0/device/color_filter_kbb"

    fun setTemperature(temperature: Int) {
        if (temperature == getTemperature())
            return

        val color = ImageUtils.instance.getRGBFromK(temperature)
        with(mSU) {
            writeFile(sRedFilter, (color.red() * 255 + 0.5).toInt().toString(16))
            writeFile(sGreenFilter, (color.green() * 255 + 0.5).toInt().toString(16))
            writeFile(sBlueFilter, (color.blue() * 255 + 0.5).toInt().toString(16))
        }
        mPrefs.edit().putInt(Constants.prefsTemperatureKey, temperature).apply()
    }

    fun getTemperature(): Int = mPrefs.getInt(Constants.prefsTemperatureKey, 5500)
}