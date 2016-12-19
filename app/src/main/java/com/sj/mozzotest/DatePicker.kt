package com.sj.mozzotest

import android.app.DatePickerDialog.OnDateSetListener
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.DatePicker
import java.util.*


/**
 * Created by Sushant on 19-12-2016.
 */

class DatePicker(onDateSetListener: OnDateSetListener) : DialogFragment(),DatePickerDialog.OnDateSetListener {


    val odsl=onDateSetListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(getActivity(), this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        odsl.onDateSet(p1,p2,p3)
    }

    interface OnDateSetListener{
        fun onDateSet(p1:Int,p2:Int,p3:Int)
    }

}