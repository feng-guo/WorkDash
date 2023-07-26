package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.workdash.routes.LOCATION_ID_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.viewModels.LocationViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapOfJobs(
    navController: NavController,
) {
    val navBackStackEntry = navController.currentBackStackEntry
    val locationId = navBackStackEntry?.arguments?.getString(LOCATION_ID_ARG) ?: ""
    val locationViewModel = LocationViewModel()
    val default = LatLng(43.4720424, -80.5371821)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(default, 15f)
    }
    val coordinates = remember {locationViewModel.getCoordinateList()}
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                title = {
                    Text("Job Map")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            coordinates.forEach { coordinate ->
                Marker(
                    state = rememberMarkerState(
                        position = LatLng(
                            coordinate.latitude,
                            coordinate.longitude
                        )
                    ),
                    title = locationViewModel.getLocation(coordinate.locationId).locationName,
                    snippet = locationViewModel.getLocation(coordinate.locationId).address.address,
                    onInfoWindowLongClick = {
                        navController.navigate(
                            route = ScreenRoute.JobDetailsWorker.passJobIdAndLocationId(
                                locationViewModel.getJobFromLocation(coordinate.locationId).jobId,
                                coordinate.locationId
                            )
                        )
                    }
                )
            }
        }
    }
}