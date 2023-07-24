package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.example.workdash.routes.JOB_ID_ARG
import com.example.workdash.routes.LOCATION_ID_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.JobApplicationService
import com.example.workdash.services.JobService
import com.example.workdash.services.LocationService
import com.example.workdash.viewModels.JobViewModel
import com.example.workdash.viewModels.LocationViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JobDetailsWorkerScreen(
    navController: NavController
) {


    val navBackStackEntry = navController.currentBackStackEntry
    val jobId = navBackStackEntry?.arguments?.getString(JOB_ID_ARG) ?: ""
    val locationId = navBackStackEntry?.arguments?.getString(LOCATION_ID_ARG) ?: ""

    var jobModel = JobModel()
    val jobCallback = { job: JobModel? -> jobModel = job?:JobModel()}
    JobService.getJobFromId(jobId, jobCallback)

    var locationModel = LocationModel()
    val locationCallback = { location: LocationModel? -> locationModel = location?:LocationModel()}
    LocationService.getLocationFromId(locationId, locationCallback)



    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Job Details")
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
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { /* Handle card click */ },
                elevation = 4.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    //TODO real image
                    AsyncImage(
                        model = "https://perkinswill.com/wp-content/uploads/2019/07/project_Eng5_7_01-2880x1570.jpg",
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Job details:",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Job Position: ",
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Employer Name: ",
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Address: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            //TODO add more to the address
                            text = locationModel.address.address,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Time: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = jobModel.schedule,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Pay: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = jobModel.payPerHour.toString(),
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Requirement(s): ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = jobModel.certificationsRequired,
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "Positions Left: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = (jobModel.totalPositionsRequired-jobModel.totalPositionsFilled).toString(),
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        JobApplicationService.applyToJob(jobModel.jobId)
                        //TODO: BUG
                        navController.navigate(route = ScreenRoute.Report.passId(locationId)) {
                            popUpTo(ScreenRoute.Report.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                )
                {
                    Text(text = "Apply", color = Color.Black)
                }
                IconButton(onClick = {
                    navController.navigate(ScreenRoute.MapOfJobs.passLocationId(locationId))
                }) {
                    Icon(Icons.Default.Map, contentDescription = "Map")
                }
            }
        }
    }
}