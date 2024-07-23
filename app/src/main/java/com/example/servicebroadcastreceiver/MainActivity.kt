package com.example.servicebroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var batteryStatusChangesBroadcastReceiver: BatteryStatusChangesBroadcastReceiver

    private lateinit var statusTextView: TextView
    private lateinit var startServiceButton: Button
    private lateinit var stopServiceButton: Button
    private val counterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val counter = intent?.getIntExtra("counter", 0)
            statusTextView.text = "Counter: $counter"
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        batteryStatusChangesBroadcastReceiver = BatteryStatusChangesBroadcastReceiver()

        initServiceUI()

    }

    fun initServiceUI() {
        statusTextView = findViewById(R.id.statusTextView)
        startServiceButton = findViewById(R.id.startServiceButton)
        stopServiceButton = findViewById(R.id.stopServiceButton)

        startServiceButton.setOnClickListener {
            startService(Intent(this, CountService::class.java))
        }

        stopServiceButton.setOnClickListener {
            stopService(Intent(this, CountService::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryStatusChangesBroadcastReceiver, filter)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(counterReceiver, IntentFilter("com.example.simpleserviceexample.COUNTER_UPDATED"), RECEIVER_EXPORTED)
        }
        else {
            registerReceiver(counterReceiver, IntentFilter("com.example.simpleserviceexample.COUNTER_UPDATED"), RECEIVER_NOT_EXPORTED)
        }


    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryStatusChangesBroadcastReceiver)
    }

}