package com.mamaocho.taller2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mamaocho.taller2.data.local.database.dao.FamilyDao
import com.mamaocho.taller2.data.local.database.dao.PersonalDao
import com.mamaocho.taller2.data.local.database.entity.Family
import com.mamaocho.taller2.data.local.database.entity.Personal

@Database(entities = [Family::class, Personal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun familyDao(): FamilyDao
    abstract fun personalDao(): PersonalDao
}
