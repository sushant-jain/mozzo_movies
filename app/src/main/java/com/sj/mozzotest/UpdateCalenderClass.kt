package com.sj.mozzotest

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import java.util.*
import android.content.ContentValues
import android.provider.CalendarContract
import android.util.Log


/**
 * Created by Sushant on 19-12-2016.
 */
class UpdateCalenderClass(context: Context) :AsyncTask<Bundle,Int,Long>() {

    val c=context
    var calenderId:Long=10
    var projection = arrayOf(Calendars._ID, Calendars.NAME, Calendars.ACCOUNT_NAME, Calendars.ACCOUNT_TYPE)

    override fun doInBackground(vararg p0: Bundle): Long {
        val calCursor = c.getContentResolver().query(Calendars.CONTENT_URI,projection,Calendars.VISIBLE + " = 1",null,Calendars._ID + " ASC")
        if(calCursor.moveToFirst()){
            calenderId=calCursor.getLong(0)
            Log.d("TAG","name"+calCursor.getString(1))
        }

        var cal=GregorianCalendar(p0[0].getInt("yearKey",2016),p0[0].getInt("monthKey",11),p0[0].getInt("dayKey",19))
        cal.timeZone= TimeZone.getTimeZone("UTC")
        cal.set(Calendar.HOUR,p0[0].getInt("hourKey"))
        cal.set(Calendar.MINUTE,p0[0].getInt("minuteKey"))
        val start = cal.timeInMillis
        val values = ContentValues()
        values.put(CalendarContract.Events.DTSTART, start)
        values.put(CalendarContract.Events.DTEND, start+100000)
        values.put(CalendarContract.Events.CALENDAR_ID,calenderId)
        values.put(CalendarContract.Events.TITLE,"SJ")
        values.put(CalendarContract.Events.EVENT_TIMEZONE,"IST")

        val uri = c.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values)
        val eventId = uri.getLastPathSegment().toLong()

        return eventId
    }

    override fun onPostExecute(result: Long) {
        Log.d("TAG","Caleneder created "+result)
    }
}