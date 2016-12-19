package com.sj.mozzotest

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kontakt.sdk.android.common.profile.IEddystoneDevice
import kotlinx.android.synthetic.main.cardlayout.*
import kotlinx.android.synthetic.main.cardlayout.view.*
import java.util.*

/**
 * Created by Sushant on 17-12-2016.
 */
class BeaconAdapter(beacons:ArrayList<IEddystoneDevice>) : RecyclerView.Adapter<BeaconAdapter.ViewHolder>(){
    val mBeacon=beacons
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUI(mBeacon[position])
    }

    override fun getItemCount(): Int {
       return mBeacon.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val customView=LayoutInflater.from(parent.context).inflate(R.layout.cardlayout,parent,false)
        val vh=ViewHolder(customView)

        with(customView){
        buttonCard.setOnClickListener {

            val i = Intent(parent.context, ContactsActivity::class.java)
            startActivity(parent.context,i,null)

        }}
        return vh
    }



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun setUI(mBeacon : IEddystoneDevice){
            with((itemView)){
                Log.d("MAIN","settingUI")
                uid.text ="UID = "+mBeacon.uniqueId
                distance.text = "Distance = "+mBeacon.distance.toFloat()

            }
        }
    }
}