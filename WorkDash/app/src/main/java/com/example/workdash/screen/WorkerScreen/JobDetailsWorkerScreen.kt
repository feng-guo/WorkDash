package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import androidx.compose.material.Button
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.workdash.routes.ScreenRoute


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun JobDetailsWorkerScreen(
    navController: NavController,
    //jobs: List<Job>
) {
    Scaffold(
        topBar = {
            TopAppBar(
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
                            text = "Line Cook",
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
                            text = "Burger King",
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
                            text = "E7",
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
                            text = "July 3rd 13:00 - 19:00",
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
                            text = "$17/hr",
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
                            text = "Requirement: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = "Food Handler Certificate",
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
                            text = "Position Left: ",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Text(
                            text = "2",
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
                        navController.navigate(route = ScreenRoute.ListOfJobsApplied.route) {

                            popUpTo(ScreenRoute.JobDetailsWorker.route){
                                inclusive = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                )
                {
                    Text(text = "Apply", color = Color.Black)
                }
            }
        }
    }
}