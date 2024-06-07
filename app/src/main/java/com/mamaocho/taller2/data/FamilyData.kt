package com.mamaocho.taller2.data

import androidx.compose.runtime.mutableStateListOf
import com.mamaocho.taller2.data.local.database.entity.Family
import com.mamaocho.taller2.data.local.database.entity.Personal

val familyList = mutableStateListOf<Family>()

var personList = mutableStateListOf<Personal>()