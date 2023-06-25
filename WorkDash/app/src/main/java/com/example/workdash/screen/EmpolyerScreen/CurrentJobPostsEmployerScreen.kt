package com.example.workdash.screen.EmpolyerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.R
import com.example.workdash.models.ScreenRoute

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CurrentJobPostsEmployerScreen(
    navController: NavController,
    //jobs: List<Job>
) {
    Scaffold(
        topBar = {
            TopAppBar(
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
                    IconButton(onClick = { /* Handle plus button click */ }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            )
        }
    ) {
            LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(jobs) { job ->
                JobCard(job = job, navController = navController)
            }
        }
    }
}

val jobs = listOf(
        Job(
//            imageResId = R.drawable.job_image1,
            imageResId = 0,
            title = "Job Title 1",
            location = "Job Location 1",
            currentState = "Current State 1"
        ),
        Job(
//            imageResId = R.drawable.job_image2,
            imageResId = 0,
            title = "Job Title 2",
            location = "Job Location 2",
            currentState = "Current State 2"
        ),
        Job(
//            imageResId = R.drawable.job_image3,
            imageResId = 0,
            title = "Job Title 3",
            location = "Job Location 3",
            currentState = "Current State 3"
        )
    )
@Composable
fun JobCard(job: Job, navController: NavController) {
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
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    //painter = painterResource(id = job.imageResId),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Job Image",
                    modifier = Modifier.size(80.dp)
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
                            text = job.title,
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
                            text = job.location,
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
                            text = job.currentState,
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
                    ),
                onClick = {
                    navController.navigate(
                        ScreenRoute.JobDetailsEmployer.route
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

data class Job(
    val imageResId: Int,
    val title: String,
    val location: String,
    val currentState: String
)

//@Preview
//@Composable
//fun CurrentJobPostsEmployerScreenPreview() {
//    val jobs = listOf(
//        Job(
////            imageResId = R.drawable.job_image1,
//            imageResId = 0,
//            title = "Job Title 1",
//            location = "Job Location 1",
//            currentState = "Current State 1"
//        ),
//        Job(
////            imageResId = R.drawable.job_image2,
//            imageResId = 0,
//            title = "Job Title 2",
//            location = "Job Location 2",
//            currentState = "Current State 2"
//        ),
//        Job(
////            imageResId = R.drawable.job_image3,
//            imageResId = 0,
//            title = "Job Title 3",
//            location = "Job Location 3",
//            currentState = "Current State 3"
//        )
//    )
//    CurrentJobPostsEmployerScreen(jobs = jobs)
//}
