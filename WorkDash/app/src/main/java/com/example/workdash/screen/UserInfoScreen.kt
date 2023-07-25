package com.example.workdash.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.workdash.models.WorkerProfileModel
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.viewModels.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserInfo(
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userViewModel: UserViewModel = viewModel()
    val userList = remember { mutableStateListOf<WorkerProfileModel>() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (currentUser != null) {
            if (userList.isEmpty()) {
                // Fetch data if the userList is empty
                userList.addAll(userViewModel.getUserList())
            }

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (userList.isEmpty()) {
                    userList.addAll(userViewModel.getUserList())
                    // Show a loading indicator or placeholder while data is being fetched
                } else {
                    val current = userList.firstOrNull { user -> user.email == currentUser.email }
                    // Data is available, show the content
                    Log.d("pfp", current!!.toString())

                    if(current?.isWorker == true) {
                        AsyncImage(
                            model = current.profilePic,
                            contentDescription = "profile",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "${current.name}",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Email: ${current.email}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Phone: ${current.phone}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Address: ${current.address}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Start Time: ${current.startTime}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "End Time: ${current.endTime}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Work Days: ${current.workDays}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Salary: ${current.salary}CAD/hr",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "ID: ${current.selectedId}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    else if(current?.isWorker != true) {
                        AsyncImage(
                            model = current.profilePic,
                            contentDescription = "profile",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "${current.name}",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Email: ${current.email}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Phone: ${current.phone}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "Address: ${current.address}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "ID: ${current.selectedId}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // User is signed in, you can access their information
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