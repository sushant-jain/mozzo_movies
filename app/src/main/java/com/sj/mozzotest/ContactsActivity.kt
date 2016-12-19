package com.sj.mozzotest

import android.accounts.Account
import android.accounts.AccountManager
import android.app.DatePickerDialog
import android.app.DialogFragment
import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_contacts.*
import android.content.ContentResolver.SYNC_EXTRAS_MANUAL
import android.content.ContentResolver
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.list_layout.view.*


class ContactsActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private val FROM_COLUMNS = arrayOf<String>(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    else
        ContactsContract.Contacts.DISPLAY_NAME)

    private val PROJECTION = arrayOf<String>(ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
            else
                ContactsContract.Contacts.DISPLAY_NAME)


    private val TO_IDS = intArrayOf(R.id.lvtv)

    var mAdapter: SimpleCursorAdapter? = null

    val bundle:Bundle=Bundle()

    val DAY_KEY="dayKey"
    val MONTH_KEY="monthKey"
    val YEAR_KEY="yearKey"
    val HOUR_KEY="hourKey"
    val MINUTE_KEY="minuteKey"
    val ATTENDEES_KEY="attendeesKey"
    val CALENDER_ID_KEY="callenderIdKey"
    val CONTEXT_KEY="contextKey"

    val AUTHORITY="com.sj.mozzotest.StubProvider.provider"
    val ACCOUNTTYPE="example.com"

    val ACCOUNT = "default_account"

    var mAccount:Account?=null

    var selectedArray= arrayListOf("You")
    var ticketCount=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        mAdapter = SimpleCursorAdapter(applicationContext, R.layout.list_layout, null, FROM_COLUMNS, TO_IDS, 0)
        listView.setAdapter(mAdapter)

        val selectedAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selectedArray)
        selectedLv.adapter=selectedAdapter

          listView.setOnItemClickListener({ parent, view, position, id ->
              with(view){
                  selectedArray.add(lvtv.text.toString())
                  ticketCount++
                  ticketNo.text=ticketCount.toString()
                  selectedAdapter.notifyDataSetChanged()
              }
          })

        loaderManager.initLoader(1, null, this)

        mAccount=createSyncAccount(this)

        timePickerButton.setOnClickListener { chooseTime() }
        datePickerButton.setOnClickListener { chooseDate() }
        bookTicket.setOnClickListener {
            Log.d("TAG","Book clicked")
            setCalander()
            val settingsBundle = Bundle()
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true)
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true)
            ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle)
            Toast.makeText(this,"Adding Event to Calender",Toast.LENGTH_SHORT)
        }


    }


    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        Log.d("TAG", "onCeeateLoader")
        return CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, ContactsContract.Contacts.DISPLAY_NAME)
    }

    override fun onLoadFinished(p0: Loader<Cursor>?, p1: Cursor?) {
        Log.d("TAG", "onLoadFinished")
        mAdapter?.swapCursor(p1)
        if (p1 != null)
            for (i in p1.columnNames)
                Log.d("TAG", i)
        Log.d("TAG", p1?.count.toString())
        mAdapter?.notifyDataSetChanged()
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {
        mAdapter?.swapCursor(null)
    }

    fun chooseTime(){
        val timeFragment:DialogFragment=TimePicker(object :TimePicker.OnTimeSetListener{
            override fun onTimeSet(hour: Int, min: Int) {
                Log.d("TAG", "hour=$hour min=$min")
                bundle.putInt(HOUR_KEY,hour)
                bundle.putInt(MINUTE_KEY,min)
            }

        })
        timeFragment.show(fragmentManager,"Time Picker")
    }

    fun chooseDate(){
        val dateFragment:DialogFragment=DatePicker(object :DatePicker.OnDateSetListener{
            override fun onDateSet(p1: Int, p2: Int, p3: Int) {
                Log.d("TAG","year=$p1 mont=$p2 day=$p3")
                bundle.putInt(DAY_KEY,p3)
                bundle.putInt(MONTH_KEY,p2)
                bundle.putInt(YEAR_KEY,p1)
            }

        })
        dateFragment.show(fragmentManager,"DatePicker")
    }

    fun setCalander(){
        val ucc=UpdateCalenderClass(this)
        ucc.execute(bundle)

    }

    fun createSyncAccount(context: Context):Account{
        val account=Account(ACCOUNT,ACCOUNTTYPE)
        val accountManager=context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        if(accountManager.addAccountExplicitly(account,null,null)){
            Log.d("TAG","Account Created")

        }
        return account
    }
}
