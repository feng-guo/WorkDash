package com.example.workdash.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workdash.routes.IS_WORKER_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.UserService.getCurrentUserId
import com.example.workdash.viewModels.CheckInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController
    //onLoginClicked: (emailOrPhone: String, password: String) -> Unit
) {

    val navBackStackEntry = navController.currentBackStackEntry
    val workerPage = navBackStackEntry?.arguments?.getBoolean(IS_WORKER_ARG) ?: true

    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    //test
    val vm = CheckInViewModel()
    var list = vm.currentMatchedJob

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "WorkDash",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { it ->  emailOrPhone = it},
            label = { Text("Email/Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Forgot password?", modifier = Modifier.clickable { /* Handle click event */ })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (emailOrPhone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(contextForToast, "Invalid Inputs", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(emailOrPhone, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Login successful, read from the user's data
                                val database: DatabaseReference = FirebaseDatabase.getInstance().reference
                                val currentUserUid = getCurrentUserId()
                                val query = database.child("userProfile").orderByChild("uid").equalTo(currentUserUid)

                                // Read the data from the database
                                query.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (snapshot in dataSnapshot.children) {
                                            // Access the value of isWorker from each matching employer profile
                                            val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
                                            if (isWorker == true) {
                                                navController.navigate(ScreenRoute.ListOfJobs.route) {
                                                    popUpTo(ScreenRoute.ListOfJobs.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            } else {
                                                navController.navigate(ScreenRoute.CurrentJobPostsEmployer.route) {
                                                    popUpTo(ScreenRoute.CurrentJobPostsEmployer.route) {
                                                        inclusive = true
                                                    }
                                                }
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
                            } else {
                                // Login failed, display an error message to the user
                                Toast.makeText(
                                    contextForToast,
                                    "Login failed. Please check your credentials.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
        },
            modifier = Modifier.height(40.dp).width(100.dp)
        ) {
            Text("Log In")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
//                Toast.makeText(
//                    contextForToast,
//                    "$workerPage",
//                    Toast.LENGTH_SHORT
//                ).show()

                if (workerPage) {
                    navController.navigate(route = ScreenRoute.UserDetailsWorker.route) {
                   // navController.navigate(route = ScreenRoute.Quiz.route) {

                        popUpTo(ScreenRoute.UserDetailsWorker.route){
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(route = ScreenRoute.SignUpEmployer.route) {

                        popUpTo(ScreenRoute.Login.route){
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier.height(40.dp).width(100.dp)
        ) {
            Text("Sign Up")
        }

    }

}

//@Preview
//@Composable
//fun LoginPagePreview() {
//    LoginScreen(
//        navController = rememberNavController(),
////        onLoginClicked = { emailOrPhone, password ->
////        // Perform login logic here
////    }
//    )
//}