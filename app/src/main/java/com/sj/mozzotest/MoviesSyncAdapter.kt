package com.sj.mozzotest

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 * Created by Sushant on 19-12-2016.
 */
class MoviesSyncAdapter(context: Context,autoInitialize:Boolean) :AbstractThreadedSyncAdapter(context,autoInitialize){
    val mc=context
    override fun onPerformSync(p0: Account?, p1: Bundle?, p2: String?, p3: ContentProviderClient?, p4: SyncResult?) {
        Log.d("TAG","syncying")
        Toast.makeText(mc,"Syncing",Toast.LENGTH_SHORT)
    }
}