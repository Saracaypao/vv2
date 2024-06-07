package com.mamaocho.taller2.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personalRegister")
data class Personal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val familyId: Int, // Clave foránea que referencia a Family.id
    val dui: Int,
    val name: String,
    val birthDate: String,
    val schoolGrade: String,
    val alphabet: String
)
