package com.example.workdash.screen.EmployerScreen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.services.AddressService
import com.example.workdash.services.LocationService

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddLocationEmployerScreen(
    navController: NavController
) {
    var locationName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var province by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    val contextForToast = LocalContext.current.applicationContext

//    var verification by remember { mutableStateOf("") }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
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
                    value = locationName,
                    onValueChange = { locationName = it},
                    label = { androidx.compose.material3.Text("Location Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { androidx.compose.material3.Text("Address") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { androidx.compose.material3.Text("City") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                //TODO this should probably be a dropdown
                OutlinedTextField(
                    value = province,
                    onValueChange = { province = it },
                    label = { androidx.compose.material3.Text("Province") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                //TODO This should also probably be a dropdown
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { androidx.compose.material3.Text("Country") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    label = { androidx.compose.material3.Text("Postal Code") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                //TODO add verification to a location
//                OutlinedTextField(
//                    value = verification,
//                    onValueChange = { verification = it },
//                    label = { androidx.compose.material3.Text("Verification") },
//                    visualTransformation = PasswordVisualTransformation()
//                )
//                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val addressModel = AddressService.createAddress(address, city, province, country, postalCode)
                    if (AddressService.verifyAddress(addressModel)) {
                        //TODO add image upload
                        val imgUrl = "https://media.cnn.com/api/v1/images/stellar/prod/230629010804-01-university-of-waterloo-sign-062823.jpg"
                        LocationService.addLocation(locationName, addressModel, imgUrl)
                    } else {
                        Toast.makeText(
                            contextForToast,
                            //TODO make this not bad
                            "This is not a real location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = "    Add    ")
                }
            }
        }
    }
}