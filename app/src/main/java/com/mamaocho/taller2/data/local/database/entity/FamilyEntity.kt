package com.mamaocho.taller2.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "familyRegister")
data class Family(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val canton: String,
    val housingType: String,
    val risk: String
)

