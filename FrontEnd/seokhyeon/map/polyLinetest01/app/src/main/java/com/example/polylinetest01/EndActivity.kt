package com.example.polylinetest01

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class EndActivity : AppCompatActivity() {

    private lateinit var timeRecordText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        var value1 = intent.getIntExtra("timeRecord",0)
        timeRecordText = findViewById(R.id.TimeRecord)
        timeRecordText.text = value1.toString()
    }
}