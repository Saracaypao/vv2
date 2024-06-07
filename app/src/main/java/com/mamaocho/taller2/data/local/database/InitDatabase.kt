package com.mamaocho.taller2.data.local.database

import android.app.Application
import androidx.room.Room

class InitDatabase : Application() {
    companion object{
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "AppDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}