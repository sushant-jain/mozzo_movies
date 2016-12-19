package com.sj.mozzotest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace
import com.mozzobytes.app.android.nearby.nearbyBeaconScanner
import kotlinx.android.synthetic.main.activity_main_kotlin.*
import kotlinx.android.synthetic.main.cardlayout.*
import kotlinx.android.synthetic.main.content_main_kotlin.*
import java.util.*

class MainKotlinActivity : AppCompatActivity(),EddystoneListener {

    var beaconsArray=ArrayList<IEddystoneDevice>()
    var proximityManager :ProximityManagerContract? =null
    val mAdapter=BeaconAdapter(beaconsArray)
    val TAG="MAIN"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)


        KontaktSDK.initialize("YBVNhCQdbYqQtkxOZyAPmyshZkQXhKUl")

        proximityManager=ProximityManager(this)
        proximityManager?.setEddystoneListener(this)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Scanning Beacons", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            proximityManager?.connect { proximityManager?.startScanning() }
            fab.setImageResource(android.R.drawable.ic_media_pause)
        })

        val layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=mAdapter

        mAdapter.notifyDataSetChanged()



    }

    override fun onStart() {
        super.onStart()

    }

    override fun onEddystonesUpdated(eddystones: MutableList<IEddystoneDevice>?, namespace: IEddystoneNamespace?) {
//        beaconsArray= eddystones as ArrayList<IEddystoneDevice>
  //      mAdapter.notifyDataSetChanged()
    }

    override fun onEddystoneDiscovered(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {
        Log.d(TAG,"discovered")
        if(eddystone!=null)
        beaconsArray.add(eddystone);
        mAdapter.notifyDataSetChanged()
    }

    override fun onEddystoneLost(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {

    }
}
