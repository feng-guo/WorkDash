package com.example.workdash.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workdash.routes.ScreenRoute
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            "WorkDash",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "What you want to do?",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if(auth.currentUser == null){
                    navController.navigate(route = ScreenRoute.Login.passIsWorker(true)) {
                        popUpTo(ScreenRoute.Login.route) {
                            inclusive = true
                        }
                    }
//                    navController.navigate(route = ScreenRoute.Report.route) {
//                        popUpTo(ScreenRoute.Report.route){
//                            inclusive = true
//                        }
//                    }
                }
                else{
                    navController.navigate(ScreenRoute.ListOfJobs.route){
                        popUpTo(ScreenRoute.ListOfJobs.route) {
                            inclusive = true
                        }
                    }
                }

            },
            Modifier.width(280.dp)
        ) {
            Text(
                text = "I want to find a job",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if(auth.currentUser == null){
                    navController.navigate(route = ScreenRoute.Login.passIsWorker(false)) {
                        popUpTo(ScreenRoute.Login.route){
                            inclusive = true
                        }
                    }
                }
                else {
                    navController.navigate(ScreenRoute.CurrentJobPostsEmployer.route) {
                        popUpTo(ScreenRoute.CurrentJobPostsEmployer.route) {
                            inclusive = true
                        }
                    }
                }

            },
            Modifier.width(280.dp)
        ) {
            Text(
                text = "I want to post a job",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

    }
}