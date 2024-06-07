package com.mamaocho.taller2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.mamaocho.taller2.data.local.database.entity.Family
import com.mamaocho.taller2.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyRegistrationScreen(
    viewModel: MainViewModel, onRegisterClick: () -> Unit
) {
    var community by remember { mutableStateOf("") }
    var housingType by remember { mutableStateOf("") }
    var familyRisk by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registro de Familia",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = community,
                onValueChange = { newValue -> community = newValue },
                label = { Text("Cantón") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = housingType,
                onValueChange = { newValue -> housingType = newValue },
                label = { Text("Tipo de Vivienda") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = familyRisk,
                onValueChange = { newValue -> familyRisk = newValue },
                label = { Text("Riesgo Familiar") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    viewModel.insertFamily(Family(canton = community, housingType = housingType, risk = familyRisk))
                    viewModel.getAllFamilies() // <-- Actualiza la lista después del registro
                    onRegisterClick() // Navega a la pantalla de listado o al Home
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text("Registrar familia")
            }
        }
    }
}


