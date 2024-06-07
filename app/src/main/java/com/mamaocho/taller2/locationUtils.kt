package com.mamaocho.taller2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    activity: ComponentActivity,
    onPermissionGranted: (Pair<Double, Double>) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(key1 = permissionState) {
        permissionState.launchMultiplePermissionRequest()
    }

    if (permissionState.allPermissionsGranted) {
        GetLocation(activity, onPermissionGranted)
    } else {
        onPermissionDenied()
    }
}

@SuppressLint("MissingPermission")
@Composable
fun GetLocation(
    activity: ComponentActivity,
    onLocationReceived: (Pair<Double, Double>) -> Unit
) {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    var location by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    DisposableEffect(Unit) {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc ->
                loc?.let {
                    location = Pair(it.latitude, it.longitude)
                }
            }
            .addOnFailureListener {
                // Handle failure
            }
        onDispose { }
    }

    location?.let {
        onLocationReceived(it)
    }
}
