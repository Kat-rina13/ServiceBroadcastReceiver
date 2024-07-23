package com.example.servicebroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BatteryStatusChangesBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra("level", 0)
            if (level < 20 && context != null) {
                val serviceIntent = Intent(context, CountService::class.java)
                context.startService(serviceIntent)
                Toast.makeText(context, "Service started due to low battery", Toast.LENGTH_LONG).show()
            }
        }
    }



}