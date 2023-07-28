package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.workdash.models.JobModel
import com.example.workdash.models.LocationModel
import com.example.workdash.models.MatchedJobModel
import com.example.workdash.routes.JOB_ID_ARG
import com.example.workdash.routes.LOCATION_ID_ARG
import com.example.workdash.services.CheckInService
import com.example.workdash.services.JobService
import com.example.workdash.services.LocationService
import com.example.workdash.viewModels.CheckInSysWorkerViewModel
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InProcessWorkerScreen(
    navController: NavController
) {


    val navBackStackEntry = navController.currentBackStackEntry
    val jobId = navBackStackEntry?.arguments?.getString(JOB_ID_ARG) ?: ""
    val locationId = navBackStackEntry?.arguments?.getString(LOCATION_ID_ARG) ?: ""

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val contextForToast = LocalContext.current
    var jobModel = JobModel()
    val jobCallback = { job: JobModel? -> jobModel = job?:JobModel()}
    JobService.getJobFromId(jobId, jobCallback)

    var matchedJobModel by remember { mutableStateOf(MatchedJobModel())}
    val matchedJobCallback = { matchedJob: MatchedJobModel? -> matchedJobModel = matchedJob?: MatchedJobModel() }
    CheckInService.getMatchedJobFromJobIdAndEmployeeId(jobId + "_" + currentUserUid, matchedJobCallback)
    val condition1 = remember { mutableStateOf(false)}
    val condition2 = remember { mutableStateOf(false)}


    var locationModel = LocationModel()
    val locationCallback = { location: LocationModel? -> locationModel = location?:LocationModel()}
    LocationService.getLocationFromId(locationId, locationCallback)

    val checkInSysWorkerViewModel = CheckInSysWorkerViewModel()



    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                title = {
                    Text("Your Job Details")
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
                        model = locationModel.imgUrl,
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
                            //TODO not sure what we should put as the location name
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
                            //TODO convert this row into a list or combine the list
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(!matchedJobModel.checkInState.toBoolean()){
                    Button(
                        onClick = {
                            checkInSysWorkerViewModel.checkIn(jobId, currentUserUid)
                            condition1.value = true
                            Toast.makeText(contextForToast, "Check In Success", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    )
                    {
                        Text(text = "Check In", color = Color.White)
                    }
                }
                else if (matchedJobModel.checkInState.toBoolean() && !matchedJobModel.checkOutState.toBoolean()){
                    Button(
                        onClick = {
                            checkInSysWorkerViewModel.checkOut(jobId, currentUserUid)
                            condition2.value = true
                            Toast.makeText(contextForToast, "Check Out Success", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    )
                    {
                        Text(text = "Check Out", color = Color.White)
                    }
                }
                else{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {

                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                        )
                        {
                            Text(text = "Finished", color = Color.White)
                        }
                        Text(text = "Check In Time: " + matchedJobModel.checkInTime)
                        Text(text = "Check Out Time: " + matchedJobModel.checkOutTime)
                    }

                }

            }
        }
    }
}