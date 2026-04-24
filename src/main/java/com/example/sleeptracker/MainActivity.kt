package com.example.sleeptracker

import android.app.TimePickerDialog
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Linking our code to the UI
        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnSetGoal = findViewById<Button>(R.id.btnSetGoal)
        val tvStage = findViewById<TextView>(R.id.tvSleepStage)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)

        // 2. Updated Goal Setting (Calculates Goal)
        btnSetGoal.setOnClickListener {
            val timePicker = TimePickerDialog(this, { _, hour, minute ->
                // Basic goal logic: If they set a goal after 10 PM (22:00), it's valid
                if (hour >= 18 || hour <= 4) {
                    tvSummary.text = "Goal Set for $hour:$minute | Target: 8h"
                    Toast.makeText(this, "Goal Saved!", Toast.LENGTH_SHORT).show()
                } else {
                    // This is "Error Handling" for invalid inputs
                    Toast.makeText(this, "Enter a valid nighttime bedtime!", Toast.LENGTH_LONG).show()
                }
            }, 22, 0, false)
            timePicker.show()
        }

        // 3. Start Tracking
        btnStart.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

            btnStart.isEnabled = false
            btnStop.isEnabled = true
            tvStage.text = "Status: Falling Asleep..."

            // Automatic Sleep Stage detection
            chronometer.setOnChronometerTickListener {
                val elapsed = SystemClock.elapsedRealtime() - it.base
                if (elapsed > 30000) { // After 30 seconds for lab demo
                    tvStage.text = "Status: Deep Sleep"
                } else if (elapsed > 10000) { // After 10 seconds
                    tvStage.text = "Status: Light Sleep"
                }
            }
        }

        // 4. Updated Stop Tracking (Saves to Memory)
        btnStop.setOnClickListener {
            chronometer.stop()
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            tvStage.text = "Status: Awake"

            // Get the time you just slept
            val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base

            // Open the "notebook" (SharedPreferences)
            val prefs = getSharedPreferences("SleepData", MODE_PRIVATE)

            // Add current sleep to the total, and increase the session count
            val totalSleep = prefs.getLong("total_time", 0L) + elapsedMillis
            val count = prefs.getInt("count", 0) + 1

            // Save the new numbers back to the notebook
            prefs.edit().putLong("total_time", totalSleep).putInt("count", count).apply()

            // Calculate the average and show it
            val avgSeconds = (totalSleep / count) / 1000
            tvSummary.text = "Avg Sleep: $avgSeconds seconds | Sessions: $count"

            Toast.makeText(this, "Sleep Logged!", Toast.LENGTH_SHORT).show()
        }

        // 5. Reset everything
        btnReset.setOnClickListener {
            chronometer.stop()
            chronometer.base = SystemClock.elapsedRealtime()
            tvStage.text = "Status: Awake"
            tvSummary.text = "Avg. Sleep: 0h 0m"
            btnStart.isEnabled = true
            btnStop.isEnabled = false
        }
    }
}