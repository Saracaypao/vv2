package com.mamaocho.taller2.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mamaocho.taller2.data.local.database.entity.Family

@Dao
interface FamilyDao {
    @Insert
    suspend fun insertFamily(family: Family)

    @Query("SELECT * FROM familyRegister")
    suspend fun getAllFamilies(): List<Family>
}

