package com.example.workdash.screen.EmployerScreen


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workdash.routes.ScreenRoute
//import com.example.workdash.models.EmployerProfileModel
import com.example.workdash.models.WorkerProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WorkerRating(
    navController: NavController,



    //jobs: List<Job>
) {

    var rating by remember {mutableStateOf(0)}
    val contextForToast = LocalContext.current.applicationContext

    //val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //val topstack = navController.currentBackStackEntry?.destination?.id

        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val query = database.child("userProfile").orderByChild("uid").equalTo(currentUserUid)
        var is_this_a_worker: Boolean = true
        val satisfiedText = remember { mutableStateOf("") }
        // Read the data from the database
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Access the value of isWorker from each matching employer profile
                    val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
                    if (isWorker == true) {
                        is_this_a_worker = true
                        satisfiedText.value = "How satisfied are you with this job?"


                    }
                    else{
                        is_this_a_worker= false
                        satisfiedText.value = "How satisfied were you with this employee?"
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    contextForToast,
                    "Database Error: $databaseError",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


            Text(


                text = satisfiedText.value,
                style = MaterialTheme.typography.h1,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )



        Row {
//            var rating by mutableStateOf(0)
            for (i in 0..4) {
                var isHighlighted by remember { mutableStateOf(false) }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    //tint = if (i < rating) Color.Yellow else Color.Gray,
                    tint = if (isHighlighted) Color.Yellow else Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            rating = i + 1
                            isHighlighted = !isHighlighted

                            if (isHighlighted) {
                                if(satisfiedText.value == "How satisfied were you with this employee?") {
                                    navController.navigate(route = ScreenRoute.CurrentJobPostsEmployer.route) {

                                        popUpTo(ScreenRoute.CurrentJobPostsEmployer.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                else if(satisfiedText.value == "How satisfied are you with this job?") {
                                    navController.navigate(route = ScreenRoute.ListOfJobs.route) {

                                        popUpTo(ScreenRoute.ListOfJobs.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                        .offset(y = 20.dp)
                )
            }
        }
    }
}
//    val candidateViewModel = CandidateViewModel()
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text("Job Detail kaveen")
//                },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        navController.popBackStack()
//                    }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) {
//        Column() {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//                    .clickable { /* Handle card click */ },
//                elevation = 4.dp
//            ){
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    Text(
//                        text = "Job details:",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp),
//                        style = MaterialTheme.typography.body1,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Job Position: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "Line Cook",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Employer Name: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "Burger King",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Address: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "E7",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Time: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "July 3rd 13:00 - 19:00",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Pay: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "\$17/hr",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Requirement: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "Food Handler Certificate",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(4.dp)
//                    ) {
//                        Text(
//                            text = "Position Left: ",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Black
//                        )
//                        Text(
//                            text = "2",
//                            style = MaterialTheme.typography.body2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            color = Color.Gray
//                        )
//                    }
//                }
//            }
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 60.dp)
//                    .clickable { /* Handle card click */ },
//                elevation = 4.dp
//            ){
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(bottom = 8.dp)
//                ) {
//                    Text(
//                        text = "Candidates: ",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
//                        style = MaterialTheme.typography.body1,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        color = Color.Black,
//                        fontWeight = FontWeight.Bold
//                    )
//                    LazyColumn(
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                    ) {
//                        items(candidateViewModel.getCandidateList()) { candidate ->
//                            CandidateCard(candidate = candidate, navController = navController)
//                        }
//                    }
//                }
//
//            }
//        }
//
//
//    }


//@Composable
//fun CandidateCard3(candidate: CandidateModel, navController: NavController) {
//    val contextForToast = LocalContext.current.applicationContext
//
//    var enabled by remember {
//        mutableStateOf(true)
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 8.dp)
//            .clickable { /* Handle card click */ },
//        elevation = 4.dp
//    ) {
//        Column() {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = "Requirement: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Black
//                )
//                Text(
//                    text = "Requirement: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Gray
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = "Self Description: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Black
//                )
//                Text(
//                    text = "Self Description",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Gray
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = "Certification: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Black
//                )
//                Text(
//                    text = "Certification: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Gray
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//            ) {
//                Text(
//                    text = "Rating: ",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Black
//                )
//                Text(
//                    text = "4.5 / 5",
//                    style = MaterialTheme.typography.body2,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    color = Color.Gray
//                )
//
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp, bottom = 8.dp, start = 30.dp, end = 30.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Button(
//                    onClick = {
//                        Toast.makeText(contextForToast, "Accepted", Toast.LENGTH_SHORT).show()
//                        enabled = false
//
//                    },
//                    enabled = enabled,
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
//                )
//                {
//                    Text(text = "kaveen_Accept", color = Color.White)
//                }
//                Button(
//                    onClick = {
//                        Toast.makeText(contextForToast, "Rejected", Toast.LENGTH_SHORT).show()
//                        enabled = false
//                    },
//                    enabled = enabled,
//                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
//                )
//                {
//                    Text(text = "kaveen_Reject", color = Color.White)
//                }
//
//            }
//        }
//    }
//}


