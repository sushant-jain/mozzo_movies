package com.sj.mozzotest

import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import java.util.*


/**
 * Created by Sushant on 19-12-2016.
 */

class TimePicker(onTimeSetListener: OnTimeSetListener) : DialogFragment(),TimePickerDialog.OnTimeSetListener {

    val mOTSL=onTimeSetListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        mOTSL.onTimeSet(p1,p2)
    }

    interface OnTimeSetListener{
        fun onTimeSet(hour:Int,min:Int)
    }

}