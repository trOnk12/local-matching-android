package com.example.local_matching

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.local_matching.worker.LocationWorkerManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val locationWorker = LocationWorkerManager(this)

        locationWorker.startPeriodRequest()


    }

}

