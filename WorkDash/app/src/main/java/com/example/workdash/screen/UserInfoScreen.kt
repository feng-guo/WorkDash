package com.example.workdash.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workdash.models.JobModel
import com.example.workdash.models.WorkerProfileModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.screen.EmployerScreen.JobCard
import com.example.workdash.viewModels.JobViewModel
import com.example.workdash.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun UserInfo(
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userViewModel: UserViewModel = viewModel()
    val userList = remember { mutableStateListOf<WorkerProfileModel>() }

    val isDataFetched = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (currentUser != null) {
            if (userList.isEmpty()) {
                // Fetch data if the userList is empty
                userList.addAll(userViewModel.getUserList())
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (userList.isEmpty()) {
                    var p = 10
                    // Show a loading indicator or placeholder while data is being fetched
                } else {
                    // Data is available, show the content
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(userList) { user ->
                            Text("Hello ${user.name}")
                        }
                    }
                }

                // User is signed in, you can access their information
                Text("Hello ${currentUser.email}")
                Button(
                    onClick = {
                        auth.signOut()
                        navController.navigate(route = ScreenRoute.Home.route) { // NOTE: change this routing to general postings
                            popUpTo(ScreenRoute.Home.route) {
                                inclusive = true
                            }
                        }
                    },
                    Modifier.padding(top = 100.dp)
                        .width(128.dp)
                        .height(40.dp)
                ) {
                    Text("Log Out")
                }
//            LazyColumn(
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//            ) {
//                items(userViewModel.getUserList()) { user ->
//                    Text("Hello ${user.name}")
//                }
//            }
            }
            // You can perform further actions with the user's information
        } else {
            Text("You are not logged in")
        }
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    UserInfo(navController = rememberNavController())
}