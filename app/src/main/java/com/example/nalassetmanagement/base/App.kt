package com.example.nalassetmanagement.base

import android.app.Application
import com.example.nalassetmanagement.room.database.importDataFakeToDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        importDataFakeToDatabase(this)
    }
}