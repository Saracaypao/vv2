package com.mamaocho.taller2

import Navigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.mamaocho.taller2.ui.theme.Taller2Theme

class MainActivity : ComponentActivity() {
    // Crea una instancia de MainViewModel usando el delegado viewModels
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Taller2Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RequestLocationPermission(
                        activity = this,
                        onPermissionGranted = { location ->
                            // Maneja la localización recibida aquí
                            viewModel.updateLocation(location)
                        },
                        onPermissionDenied = {
                            // Maneja el caso donde los permisos fueron denegados
                        }
                    )
                    Navigation(viewModel = viewModel)
                }
            }
        }
    }
}
