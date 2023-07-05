package com.example.workdash.screen.EmployerScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.models.JobModel
import com.example.workdash.viewModels.JobViewModel
import kotlin.math.ceil

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddPostEmployerScreen(
    navController: NavController
) {
    var jobName by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var payPerHour by remember { mutableStateOf("") }
    var certificationsRequired by remember { mutableStateOf("") }

    val jobViewModel = JobViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Post")
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
        Card(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 66.dp)
                .fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth().fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = jobName,
                    onValueChange = { it ->  jobName = it},
                    label = { androidx.compose.material3.Text("Job Position") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = schedule,
                    onValueChange = { schedule = it },
                    label = { androidx.compose.material3.Text("Schedule") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = payPerHour,
                    onValueChange = { payPerHour = it },
                    label = { androidx.compose.material3.Text("Pay") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = certificationsRequired,
                    onValueChange = { it ->  certificationsRequired = it},
                    label = { androidx.compose.material3.Text("Requirements") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    //TODO change this to implement actual data
                    var jobModel = JobModel("3", ceil(Math.random()*100).toInt().toString(), jobName, "In Progress", schedule, 10, certificationsRequired, 2, 0)
                    jobViewModel.addJob(jobModel)
                }) {
                    Text(text = "    Post    ")
                }
            }
        }
    }
}