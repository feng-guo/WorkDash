package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.LocationService
import com.example.workdash.viewModels.JobViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListOfJobs(
    navController: NavController,
) {
    val jobViewModel = JobViewModel()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Job Postings")
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 60.dp),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Processing Jobs: ",
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(jobViewModel.getJobList()) { job ->
                            JobCard(job = job, navController = navController)
                        }
                    }
                }

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "New Jobs: ",
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                        )
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(jobViewModel.getJobList()) { job ->
                            JobCard(job = job, navController = navController)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun JobCard(job: JobModel, navController: NavController) {
    var locationModel = LocationModel()
    val locationCallback = { location: LocationModel? -> locationModel = location?: LocationModel() }
    LocationService.getLocationFromId(job.locationId, locationCallback)

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
                //TODO get image from location
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
                            text = job.jobName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Employer: ",
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
                            text = "Hourly Wage: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = job.payPerHour.toString(),
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
                        route = ScreenRoute.JobDetailsWorker.passJobIdAndLocationId(job.jobId, job.locationId)
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

@Composable
fun ProcessingJobCard(job: JobModel, navController: NavController) {
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
                //TODO get image from location
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
                            text = job.jobName,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Employer: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            //TODO get the employer name from the location and then business
                            text = job.jobId,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row() {
                        Text(
                            text = "Hourly Wage: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = job.payPerHour.toString(),
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
                        ScreenRoute.JobDetailsWorker.route
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