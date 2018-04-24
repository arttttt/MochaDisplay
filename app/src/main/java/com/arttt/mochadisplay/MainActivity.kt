package com.arttt.mochadisplay

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var mColorManager: ColorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(SU.instance) {
            getSuAccess()
            rootAccess()
            mColorManager = ColorManager(this, getSharedPreferences(Constants.prefsTitle, Context.MODE_PRIVATE))
        }

        setContentView(R.layout.activity_main)
        colorBar.numericTransformer = object : DiscreteSeekBar.NumericTransformer() {
            override fun transform(value: Int): Int {
                return value * 100
            }
        }
        colorBar.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {}

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {
                mColorManager.setTemperature(seekBar.progress * 100)
            }
        })
        colorBar.progress = mColorManager.getTemperature() / 100

        minK.text = getString(R.string.minimum_kelvins, colorBar.min * 100)
        maxK.text = getString(R.string.maximum_kelvins, colorBar.max * 100)
    }

    override fun onDestroy() {
        super.onDestroy()

        SU.instance.close()
    }
}
