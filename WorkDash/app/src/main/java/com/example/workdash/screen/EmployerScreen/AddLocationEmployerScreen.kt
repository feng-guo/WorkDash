package com.example.workdash.screen.EmployerScreen

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.workdash.routes.ScreenRoute
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.workdash.R
import com.example.workdash.services.AddressService
import com.example.workdash.services.LocationService
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

val storage = FirebaseStorage.getInstance()
val storageRef: StorageReference = storage.reference.child("images/locationPic")

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

    var locationImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcherImage = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        locationImageUri = uri
    }

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
                if (locationImageUri != null) {
                    Image(
                        painter = rememberImagePainter(
                            data = locationImageUri,
                            builder = {
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                    )
                }
                else {
                    Image(
                        painter = painterResource(id = R.drawable.shop),
                        contentDescription = "Pick Image",
                        modifier = Modifier
                            .size(92.dp)
                            .offset(y = 1.dp)
                            .clickable {
                                launcherImage.launch("image/*")
                            }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

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
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { androidx.compose.material3.Text("City") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = province,
                    onValueChange = { province = it },
                    label = { androidx.compose.material3.Text("Province") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.height(8.dp))
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
                Button(onClick = {
                    val addressModel = AddressService.createAddress(address, city, province, country, postalCode)
                    if (AddressService.verifyAddress(addressModel)) {
                        if (locationImageUri == null) {
                            val imgUrl = "https://media.cnn.com/api/v1/images/stellar/prod/230629010804-01-university-of-waterloo-sign-062823.jpg"
                            LocationService.addLocation(locationName, addressModel, imgUrl)
                        }
                        val uploadTask = uploadImage(locationImageUri!!)
                        uploadTask.addOnSuccessListener { taskSnapshot ->
                            val locationImageUrl = taskSnapshot.toString()
                            LocationService.addLocation(locationName, addressModel, locationImageUrl)
                        }
                    } else {
                        Toast.makeText(
                            contextForToast,
                            "This is not a real location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    navController.navigate(ScreenRoute.ChooseLocationEmployer.route)
                }) {
                    Text(text = "    Add    ")
                }
            }
        }
    }
}

fun uploadImage(imageUri: Uri): Task<Uri> {
    val imageRef = storageRef.child("${System.currentTimeMillis()}.jpg")
    return imageRef.putFile(imageUri)
        .continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }
}