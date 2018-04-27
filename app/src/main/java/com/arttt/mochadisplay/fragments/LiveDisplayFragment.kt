package com.arttt.mochadisplay.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.arttt.mochadisplay.R
import kotlinx.android.synthetic.main.live_display_fragment_layout.*
import android.widget.ArrayAdapter
import com.arttt.mochadisplay.utils.*
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar


class LiveDisplayFragment : Fragment() {

    lateinit var mColorManager: ColorManager

    companion object {
        fun newInstance(): LiveDisplayFragment {
            SU.instance.getSuAccessSync()
            return LiveDisplayFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.live_display_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(ArrayAdapter.createFromResource(context, R.array.live_display_modes, android.R.layout.simple_spinner_item)) {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            modeSelector.adapter = this
        }

        modeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val maxKelvinsValue = context!!.resources.getInteger(R.integer.max_kelvins) * 100
        val minKelvinsValue = context!!.resources.getInteger(R.integer.min_kelvins) * 100
        val maxKelvinsText = context!!.resources.getString(R.string.maximum_kelvins, maxKelvinsValue)
        val minKelvinsText = context!!.resources.getString(R.string.minimum_kelvins, minKelvinsValue)

        dayTemperatureMinTitle.text = minKelvinsText
        dayTemperatureMaxTitle.text = maxKelvinsText
        nightTemperatureMinTitle.text = minKelvinsText
        nightTemperatureMaxTitle.text = maxKelvinsText

        val numTransformer = object : DiscreteSeekBar.NumericTransformer() {
            override fun transform(value: Int): Int {
                return value * 100
            }
        }

        mColorManager = ColorManager(SU.instance)

        val progressChangeListener = object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar, value: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar) {}

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar) {
                when (seekBar) {
                    dayTemperature -> {
                        mColorManager.saveColor(context!!, seekBar.progress * 100, LiveDisplayTimeUtils.TimeType.DAY)
                    }
                    nightTemperature -> {
                        mColorManager.saveColor(context!!, seekBar.progress * 100, LiveDisplayTimeUtils.TimeType.NIGHT)
                    }
                }
            }
        }

        dayTemperature.numericTransformer = numTransformer
        dayTemperature.setOnProgressChangeListener(progressChangeListener)
        dayTemperature.progress = mColorManager.getColor(context!!, LiveDisplayTimeUtils.TimeType.DAY) / 100
        nightTemperature.numericTransformer = numTransformer
        nightTemperature.setOnProgressChangeListener(progressChangeListener)
        nightTemperature.progress = mColorManager.getColor(context!!, LiveDisplayTimeUtils.TimeType.NIGHT) / 100
    }
}
