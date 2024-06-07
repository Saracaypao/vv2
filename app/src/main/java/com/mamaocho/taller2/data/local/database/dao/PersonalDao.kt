package com.mamaocho.taller2.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mamaocho.taller2.data.local.database.entity.Family
import com.mamaocho.taller2.data.local.database.entity.Personal

@Dao
interface PersonalDao {
    @Insert
    suspend fun insertPersona(persona: Personal)

    @Query("SELECT * FROM personalRegister WHERE familyId = :familyId")
    suspend fun getPersonsByFamilyId(familyId: Int): List<Personal>

    @Query("SELECT * FROM personalRegister")
    suspend fun getAllPersons(): List<Personal>
}

