# SleepTracker Android Application 🌙

A functional Android application developed in Kotlin that monitors sleep cycles, calculates sleep duration, and helps users track their nighttime goals.

## 🚀 Key Features
* **Real-Time Chronometer:** Tracks sleep duration accurately from start to finish.
* **Automatic Sleep Stages:** Transitions status from "Awake" to "Light Sleep" and "Deep Sleep" based on elapsed time.
* **Data Persistence:** Uses `SharedPreferences` to save history so your average sleep data stays saved even after closing the app.
* **Bedtime Goal Setting:** Includes a `TimePickerDialog` with input validation to prevent invalid sleep times.
* **Modern UI:** Designed with a dark-mode theme for comfort during nighttime use.

## 🛠️ Technology Stack
* **Language:** Kotlin
* **UI Layout:** XML (ConstraintLayout & LinearLayout)
* **Storage:** SharedPreferences
* **Tools:** Android Studio, Android SDK 35, Git

## 📋 How to Use
1. **Set a Goal:** Use the "Set Bedtime Goal" button to pick your target sleep time.
2. **Start Sleep:** Press "Start" when you go to bed.
3. **Monitor:** The app will update your sleep stage automatically.
4. **Stop & Save:** Press "Stop" to end the session and update your average sleep statistics.
5. **Reset:** Use the "Reset History" button to clear all stored data.

---
*Developed as part of an Advanced Android Development Lab Project - 2026*
