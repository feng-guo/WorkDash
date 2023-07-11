package com.example.workdash.screen.WorkerScreen

import android.widget.Toast
import android.app.TimePickerDialog
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.workdash.R
import com.example.workdash.routes.ScreenRoute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar
import kotlin.math.roundToInt
import com.example.workdash.models.WorkerProfileModel
import com.google.firebase.storage.FirebaseStorage

@Composable
fun UserDetailsWorkerScreen(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }
    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")
    val workDays = remember { mutableSetOf<String>() }
    val IDs = listOf("Passport", "Driver's Licence", "Health Card", "Employee ID")
    var selectedId by remember { mutableStateOf("") }

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val fromTime = remember { mutableStateOf("") }
    val mTimePickerDialog1 = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            fromTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, true
    )
    val toTime = remember { mutableStateOf("") }
    val mTimePickerDialog2 = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            toTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, true
    )

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(630.dp)
    ) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(108.dp)
                    .background(MaterialTheme.colors.background, shape = CircleShape)
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberImagePainter(
                            data = imageUri,
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
                        painter = painterResource(id = R.drawable.profile_pic),
                        contentDescription = "Pick Image",
                        modifier = Modifier
                            .size(92.dp)
                            .align(Alignment.Center)
                            .offset(y = 1.dp)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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
            Text(text = "Salary: $sliderPosition CAD/hours", style = MaterialTheme.typography.h6)
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                valueRange = 0f..50f
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(text = "Working Days", style = MaterialTheme.typography.h6)
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    daysOfWeek.forEach { day ->
                        if (dayOfWeekItem(day)) {
                            workDays.add(day)
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)) {
                Button(
                    onClick = { mTimePickerDialog1.show() },
                    Modifier
                        .padding(horizontal = 20.dp)
                        .width(128.dp)
                        .height(40.dp)) {
                    if (fromTime.value == "")
                    {
                        Text(text = "From", color = Color.White)
                    }
                    else
                    {
                        Text(text = fromTime.value, color = Color.White)
                    }
                }

                Button(
                    onClick = { mTimePickerDialog2.show() },
                    Modifier
                        .padding(horizontal = 20.dp)
                        .width(128.dp)
                        .height(40.dp)) {
                    if (toTime.value == "")
                    {
                        Text(text = "To", color = Color.White)
                    }
                    else
                    {
                        Text(text = toTime.value, color = Color.White)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(0.dp)) {
                Text(text = "Identity Proof Selection", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    IDs.forEach { id ->
                        val isChecked = selectedId == id
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = isChecked,
                                onClick = {
                                    if (!isChecked) {
                                        selectedId = id
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

            SignUpButton(
                navController = navController,
                email = email,
                password = password,
                imageUri = imageUri,
                onImageUriChanged = { newImageUri -> imageUri = newImageUri }
            )

            // if the signup is successful, save the users information in the database
            if (auth.currentUser != null) {
                var uid = auth.currentUser!!.uid
                var workerProfile = WorkerProfileModel(
                    uid = uid,
                    isWorker = true,
                    name = name,
                    email = email,
                    phone = phone,
                    address = address,
                    salary = sliderPosition.toInt(),
                    isVerified = true,
                    workDays = workDays.toList(),
                    startTime = fromTime.value,
                    endTime = toTime.value,
                    selectedId = selectedId
                )

                Log.d("IMAGE ID", imageUri.toString())
                if(imageUri != null)
                {
                    Log.d("UID", "I AM HERE 4")
                    FirebaseStorage.getInstance().reference.child("images/profilePic/$uid").child("profilePic.jpg").putFile(imageUri!!)
                }
                FirebaseDatabase.getInstance().reference.child("userProfile").child(uid).setValue(workerProfile)
            }
        }
    }
}


@Composable
fun dayOfWeekItem(day: String): Boolean {
    var isSelected by remember { mutableStateOf(false) }

    Button(
        onClick = { isSelected = !isSelected },
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .width(48.dp)
            .height(36.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
            contentColor = MaterialTheme.colors.primaryVariant
        )
    ) {
        Text(
            text = day,
            modifier = Modifier.padding(0.dp),
            color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
        )
    }
    return isSelected
}

@Composable
fun SignUpButton(
    navController: NavController,
    email: String,
    password: String,
    imageUri: Uri?,
    onImageUriChanged: (Uri?) -> Unit
) {
    val contextForToast = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                if(imageUri != null)
                {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Signup successful, navigate to the desired screen
                                navController.navigate(route = ScreenRoute.ListOfJobs.route) {
                                    popUpTo(ScreenRoute.ListOfJobs.route) {
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
                }
                else {
                    Toast.makeText(
                        contextForToast,
                        "Profile image cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Modifier
                .padding(horizontal = 20.dp)
                .width(128.dp)
                .height(40.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
    onImageUriChanged(imageUri)
}