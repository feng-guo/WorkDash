package com.example.workdash.screen.EmployerScreen

import android.widget.Toast
import android.app.TimePickerDialog
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.models.EmployerProfileModel
import com.example.workdash.routes.ScreenRoute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SignUpEmployerScreen(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val IDs = listOf("Passport", "Driver's Licence", "Health Card", "Employee ID")
    val checkedIDs = remember { mutableStateListOf<String>() }

    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(630.dp)
    ) {
        item {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone No.") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Column(modifier = Modifier.padding(0.dp)) {
                Text(text = "Identity Proof Selection", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    IDs.forEach { id ->
                        val isChecked = checkedIDs.contains(id)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = {
                                    if (isChecked) {
                                        checkedIDs.remove(id)
                                    } else {
                                        checkedIDs.add(id)
                                    }
                                },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Text(text = id, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            SignUpButton(navController = navController, email = email, password = password)

            // if the signup is successful, save the users information in the database
            if (auth.currentUser != null) {
                var uid = auth.currentUser!!.uid
                var employerProfile = EmployerProfileModel(
                    uid = uid,
                    isWorker = false,
                    name = name,
                    email = email,
                    phone = phone,
                    address = address,
                    isVerified = true,
                )

                FirebaseDatabase.getInstance().reference.child("userProfile").child(uid).setValue(employerProfile)
            }
        }
    }
}

@Composable
fun SignUpButton(navController: NavController, email: String, password: String) {
    val contextForToast = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Signup successful, navigate to the desired screen
                            navController.navigate(route = ScreenRoute.CurrentJobPostsEmployer.route) {
                                popUpTo(ScreenRoute.CurrentJobPostsEmployer.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                            // Signup failed, display an error message to the user
                            val exception = task.exception
                            when (exception) {
                                is FirebaseAuthUserCollisionException -> {
                                    Toast.makeText(
                                        contextForToast,
                                        "Email already in use",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is FirebaseAuthException -> {
                                    Toast.makeText(
                                        contextForToast,
                                        "Signup failed: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    Toast.makeText(
                                        contextForToast,
                                        "Signup failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            },
            Modifier.padding(horizontal = 20.dp)
                .width(128.dp)
                .height(40.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}