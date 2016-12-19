package com.sj.mozzotest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MoviesSyncService : Service() {

    val moviesSyncAdapter=MoviesSyncAdapter(applicationContext,true)
    override fun onBind(intent: Intent): IBinder? {
        return moviesSyncAdapter.syncAdapterBinder
    }
}
