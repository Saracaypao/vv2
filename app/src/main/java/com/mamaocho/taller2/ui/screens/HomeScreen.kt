package com.mamaocho.taller2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mamaocho.taller2.MainViewModel
import com.mamaocho.taller2.ui.theme.Taller2Theme


@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onAddFamilyClick: () -> Unit,
    onAddPersonClick: () -> Unit
) {
    val listFamily = viewModel.listFamily.collectAsState()
    val listPersons = viewModel.listPersons.collectAsState()

    Taller2Theme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { onAddFamilyClick() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when {
                    listFamily.value.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Sin registros")
                        }
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(listFamily.value.size) { index ->
                                val family = listFamily.value[index]
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(text = "Cantón: ${family.canton}")
                                    Text(text = "Tipo de Vivienda: ${family.housingType}")
                                    Text(text = "Riesgo: ${family.risk}")
                                    TextButton(onClick = { onAddPersonClick() }) {
                                        Text("Agregar Persona")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

