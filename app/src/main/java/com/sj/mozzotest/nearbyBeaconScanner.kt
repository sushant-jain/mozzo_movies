package com.mozzobytes.app.android.nearby

/**
 * Created by Sushant on 15-12-2016.
 */

import android.content.Context
import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener
import com.kontakt.sdk.android.ble.manager.ProximityManager
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener
import com.kontakt.sdk.android.common.KontaktSDK
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace
import com.kontakt.sdk.android.common.util.Constants
import java.util.*

class nearbyBeaconScanner(val context : Context,val mBeaconApproachingListener:OnBeaconApproachingListener) :EddystoneListener {

    //val proximityManager:ProximityManagerContract= ProximityManager(context)
    val eddyStoneHM=HashMap<String?,IEddystoneDevice?>()
    val distanceHM=HashMap<String?,Double?>()
    var minDistance:Double=1000000.0
    var minUid:String="NULL"

    init {
        //proximityManager.setEddystoneListener(this)
        KontaktSDK.initialize("YBVNhCQdbYqQtkxOZyAPmyshZkQXhKUl")
    }

    //singelton for initializing the sdk call SDKinitializer.initializeSDK()
//    object SDKinitializer{
//        var i=0
//        fun initializeSDK(){
//            if(i==0){
//                KontaktSDK.initialize("YBVNhCQdbYqQtkxOZyAPmyshZkQXhKUl")
//                i++
//            }
//        }
//    }
    public object proximityManagerObject{
        var i=0
        var proximityManager:ProximityManagerContract?=null
        fun initializePM(context: Context){
            if(i==0){
                proximityManager=ProximityManager(context)
                i++
            }
        }
    }



    fun startScanning(){
        //SDKinitializer.initializeSDK()
        proximityManagerObject.initializePM(context)
        proximityManagerObject.proximityManager?.setEddystoneListener(this)
        proximityManagerObject.proximityManager?.connect { proximityManagerObject.proximityManager?.startScanning() }
    }

    fun stopScanning(){
        proximityManagerObject.proximityManager?.stopScanning()
    }

    fun disconnect(){
        proximityManagerObject.proximityManager?.disconnect()
    }

    fun getNearestBeacon()=minUid



    override fun onEddystonesUpdated(eddystones: MutableList<IEddystoneDevice>, namespace: IEddystoneNamespace?) {
        mBeaconApproachingListener.BeaconListUpdated(eddystones)
        var temp:String="NONE"
        for(ed in eddystones){
            distanceHM.put(ed.uniqueId,ed.distance)
            eddyStoneHM.put(ed.uniqueId,ed)
            if(ed.distance<minDistance){
                temp=ed.uniqueId
                minDistance=ed.distance
            }
        }
        if(temp=="NONE"){

        }else if(minUid!=temp){
            minUid=temp
            mBeaconApproachingListener.nearestBeaconUpdated()
        }
    }

    override fun onEddystoneDiscovered(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {
        eddyStoneHM.put(eddystone?.uniqueId,eddystone)
        distanceHM.put(eddystone?.uniqueId,eddystone?.distance)

        if(eddystone?.distance?:100000.0 < minDistance){
            mBeaconApproachingListener.nearestBeaconUpdated()
        }
    }

    override fun onEddystoneLost(eddystone: IEddystoneDevice?, namespace: IEddystoneNamespace?) {
        eddyStoneHM.remove(eddystone?.uniqueId)
        distanceHM.remove(eddystone?.uniqueId)
    }

    interface OnBeaconApproachingListener{
        fun nearestBeaconUpdated()
        fun BeaconListUpdated(eddystones: MutableList<IEddystoneDevice>)
    }

}