package com.example.servicebroadcastreceiver

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class CountService: Service() {
    private val handler = Handler()
    private var counter = 0
    private var isRunning = false


    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        isRunning = true
        handler.postDelayed(runnable, 10000)
        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        handler.removeCallbacks(runnable)
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()

    }

    private val runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                counter++
                val intent = Intent("com.example.simpleserviceexample.COUNTER_UPDATED")
                intent.putExtra("counter", counter)
                sendBroadcast(intent)
                handler.postDelayed(this, 10000)
            }
        }
    }

}