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
import androidx.lifecycle.MutableLiveData
import com.mamaocho.taller2.MainViewModel
import com.mamaocho.taller2.data.local.database.entity.Personal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonRegistrationScreen(
    viewModel: MainViewModel, onRegisterClick: () -> Unit, onLocationClick: () -> Unit, familyId: Int
) {
    var dui by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var educationLevel by remember { mutableStateOf("") }
    var canReadWrite by remember { mutableStateOf(false) }

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
                text = "Registro de Persona",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = dui,
                onValueChange = { newValue -> dui = newValue },
                label = { Text("Número de DUI") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = fullName,
                onValueChange = { newValue -> fullName = newValue },
                label = { Text("Nombre Completo") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = birthDate,
                onValueChange = { newValue -> birthDate = newValue },
                label = { Text("Fecha de Nacimiento") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = educationLevel,
                onValueChange = { newValue -> educationLevel = newValue },
                label = { Text("Grado Escolar") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Checkbox(
                    checked = canReadWrite,
                    onCheckedChange = { newValue -> canReadWrite = newValue },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(text = "¿Sabe leer y escribir?", modifier = Modifier.padding(start = 8.dp))
            }
            Button(
                onClick = {
                    viewModel.insertPersons(
                        listOf(
                            Personal(
                                familyId = familyId,
                                dui = dui.toIntOrNull() ?: 0,  // Convierte a Int, usa 0 si es null
                                name = fullName,
                                birthDate = birthDate,
                                schoolGrade = educationLevel,
                                alphabet = if (canReadWrite) "true" else "false",
                            )
                        )
                    )
                    viewModel.getAllPersons()
                    onRegisterClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Registrar persona", color = Color.White, fontSize = 16.sp)
            }
            Button(
                onClick = onLocationClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Registrar Ubicación", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}
