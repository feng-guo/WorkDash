package com.example.workdash.screen.EmployerScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.JobService
import com.example.workdash.services.LocationService
import com.example.workdash.viewModels.JobViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrentJobPostsEmployerScreen(
    navController: NavController,
    jobViewModel: JobViewModel = JobViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Current Jobs")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ScreenRoute.ChooseLocationEmployer.route)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) {
            LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(items = jobViewModel.jobList
            ) { job ->
                JobCard(jobModel = job, navController = navController)
            }
        }
    }
}

@Composable
fun JobCard(jobModel: JobModel, navController: NavController) {
    var locationModel = LocationModel()
    val locationCallback = { location: LocationModel? ->
        locationModel = location?: LocationModel() }
    LocationService.getLocationFromId(jobModel.locationId, locationCallback)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        bottom = 8.dp,
                        top = 16.dp,
                        start = 16.dp,
                    )
                    .weight(10f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //TODO figure out images
//                AsyncImage(
//                    model = job.location.imgUrl,
//                    contentDescription = null,
//                    modifier = Modifier.size(100.dp)
//                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Row() {
                        Text(
                            text = "Job Title: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = jobModel.jobName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Location: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = locationModel.locationName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Current state: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = jobModel.jobState,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                    )
                    .weight(1f),
                onClick = {
                    navController.navigate(
                        route = ScreenRoute.JobDetailsEmployer.passJobIdAndLocationId(jobModel.jobId, jobModel.locationId)
                    )
                }
            ) {
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = "Arrow"
                )
            }
        }
    }
}