package com.mamaocho.taller2

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamaocho.taller2.data.familyList
import com.mamaocho.taller2.data.local.database.InitDatabase
import com.mamaocho.taller2.data.local.database.entity.Family
import com.mamaocho.taller2.data.local.database.entity.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _location = MutableLiveData<Pair<Double, Double>>()
    val location: LiveData<Pair<Double, Double>> get() = _location

    fun updateLocation(location: Pair<Double, Double>) {
        _location.value = location
    }
    // Estados que gestionan el estado de la interfaz
    private val _uiState = MutableStateFlow<UiState>(UiState.Ready)
    val uiState : StateFlow<UiState> = _uiState

    private val _listFamily = MutableStateFlow<List<Family>>(emptyList())
    val listFamily = _listFamily.asStateFlow()
    private val _listPersons = MutableStateFlow<List<Personal>>(emptyList())
    val listPersons = _listPersons.asStateFlow()

    val showModal = mutableStateOf(false)
    val newCanton = mutableStateOf("")
    val newHousingType = mutableStateOf("")
    val newRisk = mutableStateOf("")
    val newDui = mutableStateOf("")
    val newName = mutableStateOf("")
    val newBirthDate = mutableStateOf("")
    val newSchoolGrade = mutableStateOf("")
    val newAlphabet = MutableLiveData<Boolean>()

    fun updateFamilies(families: List<Family>) {
        _listFamily.value = families
    }

    fun showModal() {
        showModal.value = !showModal.value
    }

    private fun cleanFieldsFamily() {
        newCanton.value = ""
        newHousingType.value = ""
        newRisk.value = ""
    }

    private fun cleanFieldsPersonal() {
        newDui.value = ""
        newName.value = ""
        newBirthDate.value = ""
        newSchoolGrade.value = ""
        newAlphabet.value = false
    }

    private val db = InitDatabase.database

    init {
        getAllFamilies()
    }

    fun insertFamily(family: Family) {
        Log.i("MainViewModel", "Ejecutando función insertFamily en viewModel")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Insertando familia
                db.familyDao().insertFamily(family)
                _uiState.value = UiState.Success("Registro de familia agregado correctamente")
                getAllFamilies() // <-- Asegúrate de actualizar la lista después de insertar
            } catch (e: Exception) {
                when (e) {
                    is SQLiteConstraintException -> {
                        Log.i("MainViewModel", "Error: código de registro duplicado")
                        _uiState.value = UiState.Error("Error: código de registro duplicado")
                    }
                    else -> {
                        Log.i("MainViewModel", e.toString())
                        _uiState.value = UiState.Error("Error al intentar acceder a la base de datos")
                    }
                }
            }
        }
    }

    fun insertPersons(personalList: List<Personal>) {
        Log.i("MainViewModel", "Ejecutando función insertPersons en viewModel")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Insertando personas
                for (person in personalList) {
                    db.personalDao().insertPersona(person)
                }
                _uiState.value = UiState.Success("Registros de personas agregados correctamente")
            } catch (e: Exception) {
                when (e) {
                    is SQLiteConstraintException -> {
                        Log.i("MainViewModel", "Error: código de registro duplicado")
                        _uiState.value = UiState.Error("Error: código de registro duplicado")
                    }
                    else -> {
                        Log.i("MainViewModel", e.toString())
                        _uiState.value = UiState.Error("Error al intentar acceder a la base de datos")
                    }
                }
            }
        }
    }

    // Select all families
    fun getAllFamilies(){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val families= db.familyDao().getAllFamilies()
                _listFamily.value = families // <-- Asegúrate de actualizar el estado
            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("Error al intentar acceder a la base de datos")
            }
        }
    }

    // Select all persons
    fun getAllPersons(){
        viewModelScope.launch (Dispatchers.IO) {
            try {
                val persons = db.personalDao().getAllPersons()
                _listPersons.value = persons
            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
                _uiState.value = UiState.Error("Error al intentar acceder a la base de datos")
            }
        }
    }

    fun addNewFamilyRegistry(){
        if (newCanton.value.isEmpty()){
            return
        }

        if (newHousingType.value.isEmpty()){
            return
        }

        if (newRisk.value.isEmpty()){
            return
        }

        val newFamilyRegistry = Family(
            canton = newCanton.value,
            housingType = newHousingType.value,
            risk = newRisk.value
        )

        viewModelScope.launch(Dispatchers.IO){
            db.familyDao().insertFamily(newFamilyRegistry)
            getAllFamilies() // <-- Asegúrate de actualizar la lista después de insertar
        }

        cleanFieldsFamily()

    }

    fun addNewPersonal(familyId: Int) {
        if (newName.value.isNullOrEmpty() || newBirthDate.value.isNullOrEmpty() || newSchoolGrade.value.isNullOrEmpty()) {
            return
        }

        val duiInt = newDui.value.toIntOrNull() ?: return
        val alphabetString = if (newAlphabet.value == true) "true" else "false"

        val newPersonal = Personal(
            familyId = familyId,
            dui = duiInt,
            name = newName.value!!,
            birthDate = newBirthDate.value!!,
            schoolGrade = newSchoolGrade.value!!,
            alphabet = alphabetString
        )

        viewModelScope.launch(Dispatchers.IO) {
            db.personalDao().insertPersona(newPersonal)
        }

        cleanFieldsPersonal()
    }

    fun setStateToReady() {
        _uiState.value = UiState.Ready
    }
}

sealed class UiState {
    data object Ready : UiState()
    data class Success (val msg : String) : UiState()
    data class Error (val msg : String) : UiState()
}
