package com.sj.mozzotest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticatorService : Service() {

    val mAuthenticator=Authenticator(applicationContext)
    override fun onBind(intent: Intent): IBinder? {
        return mAuthenticator.iBinder
    }
}
