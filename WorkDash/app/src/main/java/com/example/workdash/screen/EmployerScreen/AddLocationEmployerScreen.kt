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
import com.example.workdash.models.LocationModel
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddLocationEmployerScreen(
    navController: NavController
) {
    var propertyName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var verification by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Location")
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
                    value = propertyName,
                    onValueChange = { it ->  propertyName = it},
                    label = { androidx.compose.material3.Text("Property Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { androidx.compose.material3.Text("Address") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = verification,
                    onValueChange = { verification = it },
                    label = { androidx.compose.material3.Text("Verification") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    var locationModel = LocationModel(1, propertyName, location, "yes", "https://media.cnn.com/api/v1/images/stellar/prod/230629010804-01-university-of-waterloo-sign-062823.jpg")
                    FirebaseDatabase.getInstance().reference.child("Locations").child("Testing company").setValue(locationModel)
                }) {
                    Text(text = "    Add    ")
                }
            }
        }
    }
}