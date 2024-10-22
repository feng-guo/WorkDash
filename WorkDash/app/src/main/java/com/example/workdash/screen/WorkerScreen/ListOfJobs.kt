package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.LocationService
import com.example.workdash.viewModels.JobViewModel
import com.example.workdash.viewModels.LocationViewModel
import com.example.workdash.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListOfJobs(
    navController: NavController,
) {
    val jobViewModel = JobViewModel()
    val locationViewModel = LocationViewModel()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val userViewModel = UserViewModel()

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
                    // Refresh Button
                    IconButton(
                        onClick = {
                            navController.navigate(ScreenRoute.ListOfJobs.route){
                                popUpTo(ScreenRoute.Home.route) {
                                    inclusive = true
                                }
                            }
                                  },
                        content = {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Refresh",
                                tint = Color.White // Change the tint color as needed
                            )
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 60.dp)

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
                        text = "Your Jobs: ",
                        modifier = Modifier.padding(horizontal = 8.dp,vertical = 8.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                    )
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(jobViewModel.getMatchedJobList()) { job ->
                            ProcessingJobCard(job = job, locationModel = locationViewModel.getLocation(job.locationId), navController = navController)
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
                        modifier = Modifier.padding(horizontal = 8.dp,vertical = 8.dp),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold
                        )
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(jobViewModel.getJobListWithFilter(currentUserUid!!, userViewModel)) { job ->
                            JobCard(job = job, locationModel = locationViewModel.getLocation(job.locationId), navController = navController)
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun JobCard(job: JobModel, locationModel: LocationModel, navController: NavController) {
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
                AsyncImage(
                    model = locationModel.imgUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
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
                            text = "\$${job.payPerHour.toString()} / Hour",
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProcessingJobCard(job: JobModel, locationModel: LocationModel, navController: NavController) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(140.dp)
            .padding(bottom = 8.dp, end = 12.dp),
        elevation = 4.dp,
        onClick = {
                    navController.navigate(
                        route = ScreenRoute.InProcessWorker.passJobIdAndLocationId(job.jobId, job.locationId)
                    )
                }
    ) {

        Column() {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                Text(
                    text = "Job Name: ",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                )
                Text(
                    text = job.jobName,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = locationModel.imgUrl,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
    }
}