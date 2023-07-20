package com.example.workdash.screen.WorkerScreen

import android.widget.Toast
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import org.opencv.core.CvType.CV_32F
import java.io.InputStream

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

    var photoIdUri by remember { mutableStateOf<Uri?>(null) }
    val launcherID = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoIdUri = uri
    }

    var photoProfileUri by remember { mutableStateOf<Uri?>(null) }
    val launcherProfile = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoProfileUri = uri
    }

    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    val launcherResume = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        resumeUri = uri
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
                if (photoProfileUri != null) {
                    Image(
                        painter = rememberImagePainter(
                            data = photoProfileUri,
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
                                launcherProfile.launch("image/*")
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

            if(selectedId != "")
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(172.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            launcherID.launch("image/*")
                        }
                ) {
                    if (photoIdUri != null) {
                        Image(
                            painter = rememberImagePainter(
                                data = photoIdUri,
                            ),
                            contentDescription = "Photo ID",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else {
                        Text(
                            text = "Add $selectedId",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            AuthenticateWorker(
                profileImageUri = photoProfileUri,
                idImageUri = photoIdUri,
                onProfileImageUriChanged = { newImageUri -> photoProfileUri = newImageUri },
                onIdImageUriChanged = { newImageUri -> photoIdUri = newImageUri }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { launcherResume.launch("application/pdf") },
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = "Upload Resume")
            }

            Text(
                text = if (resumeUri != null) "Resume uploaded" else "No resume selected",
                modifier = Modifier.padding(horizontal = 20.dp),
                style = MaterialTheme.typography.caption
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignUpButton(
                navController = navController,
                email = email,
                password = password,
                profileImageUri = photoProfileUri,
                idImageUri = photoIdUri,
                onProfileImageUriChanged = { newImageUri -> photoProfileUri = newImageUri },
                onIdImageUriChanged = { newImageUri -> photoIdUri = newImageUri }
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

                if(photoProfileUri != null && photoIdUri != null)
                {
                    FirebaseStorage.getInstance().reference.child("images/profilePic/$uid").child("profilePic.jpg").putFile(photoProfileUri!!)
                    FirebaseStorage.getInstance().reference.child("images/IDPic/$uid").child("id.jpg").putFile(photoIdUri!!)
                }
                if( resumeUri != null)
                {
                    FirebaseStorage.getInstance().reference.child("resume/$uid").child("resume.pdf").putFile(resumeUri!!)
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
    profileImageUri: Uri?,
    idImageUri: Uri?,
    onProfileImageUriChanged: (Uri?) -> Unit,
    onIdImageUriChanged: (Uri?) -> Unit
) {
    val contextForToast = LocalContext.current.applicationContext
    val auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            onClick = {
                if(profileImageUri != null && idImageUri != null)
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
                else if(profileImageUri == null)
                {
                    Toast.makeText(
                        contextForToast,
                        "Profile image cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else if(idImageUri == null)
                {
                    Toast.makeText(
                        contextForToast,
                        "ID cannot be empty",
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
    onProfileImageUriChanged(profileImageUri)
    onIdImageUriChanged(idImageUri)
}

@Composable
fun AuthenticateWorker(
    profileImageUri: Uri?,
    idImageUri: Uri?,
    onProfileImageUriChanged: (Uri?) -> Unit,
    onIdImageUriChanged: (Uri?) -> Unit
) {
    if(profileImageUri != null && idImageUri != null)
    {
        val context = LocalContext.current
        OpenCVLoader.initDebug()
        val imageResourceId: Uri? = idImageUri
        val anotherImageResourceId: Uri? = profileImageUri
        if (imageResourceId != null && anotherImageResourceId != null) {
            val imageID = loadImageFromUri(context, imageResourceId)
            val imageProfile = loadImageFromUri(context, anotherImageResourceId)

            val grayImageID = convertToGray(imageID)
            val cascadeClassifier = loadFaceCascadeClassifier(context)
            val facesID = detectFaces(grayImageID, cascadeClassifier)

            if (facesID.isNotEmpty()) {
                val bestFaceRectID = chooseBestFace(facesID)

                val faceImageID = Mat(imageID, bestFaceRectID)
                val resizedFaceImageID = resizeImage(faceImageID, Size(100.0, 100.0))

                val grayImageProfile = convertToGray(imageProfile)
                val facesProfile = detectFaces(grayImageProfile, cascadeClassifier)
                val bestFaceRectProfile = chooseBestFace(facesProfile)
                val faceImageProfile = Mat(imageProfile, bestFaceRectProfile)
                val resizedFaceImageProfile = resizeImage(faceImageProfile, Size(100.0, 100.0))

                val accuracy = calculateFaceMatchAccuracy(resizedFaceImageID, resizedFaceImageProfile)
                println("Face Match Accuracy: $accuracy%")

                val resizedFaceBitmap = Bitmap.createBitmap(
                    resizedFaceImageID.cols(),
                    resizedFaceImageID.rows(),
                    Bitmap.Config.ARGB_8888
                )
                Utils.matToBitmap(resizedFaceImageID, resizedFaceBitmap)

                val grayAnotherBitmap = Bitmap.createBitmap(
                    resizedFaceImageProfile.cols(),
                    resizedFaceImageProfile.rows(),
                    Bitmap.Config.ARGB_8888
                )
                Utils.matToBitmap(resizedFaceImageProfile, grayAnotherBitmap)

                val resizedFaceState = remember { mutableStateOf(resizedFaceBitmap) }
                val grayAnotherState = remember { mutableStateOf(grayAnotherBitmap) }

                LazyColumn(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                Image(
                                    bitmap = resizedFaceState.value.asImageBitmap(),
                                    contentDescription = "Gray Another Image",
                                    modifier = Modifier
                                        .height(200.dp)
                                        .weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Image(
                                    bitmap = grayAnotherState.value.asImageBitmap(),
                                    contentDescription = "Resized Face Image",
                                    modifier = Modifier
                                        .height(200.dp)
                                        .weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Face Match Accuracy: $accuracy%",
                                style = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

            } else {
                println("No face detected in the image.")
            }
        }
    }


    onProfileImageUriChanged(profileImageUri)
    onIdImageUriChanged(idImageUri)
}

private fun loadImageFromUri(context: Context, imageUri: Uri): Mat {
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
    val imageBitmap = BitmapFactory.decodeStream(inputStream)
    val imageMat = Mat()
    Utils.bitmapToMat(imageBitmap, imageMat)
    return imageMat
}

private fun chooseBestFace(faces: List<Rect>): Rect {
    return faces.maxByOrNull { it.width * it.height } ?: faces.first()
}

private fun convertToGray(image: Mat): Mat {
    if (image.channels() > 1) {
        val grayImage = Mat()
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY)
        return grayImage
    }
    return image
}

private fun loadFaceCascadeClassifier(context: Context): CascadeClassifier {
    val cascadeClassifier = CascadeClassifier()
    val cascadeClassifierFile = File(context.cacheDir, "haarcascade_frontalface_default.xml")

    if (!cascadeClassifierFile.exists()) {
        try {
            val inputStream = context.resources.openRawResource(R.raw.haarcascade_frontalface_default)
            val outputStream = FileOutputStream(cascadeClassifierFile)

            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)

            outputStream.write(buffer)
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    cascadeClassifier.load(cascadeClassifierFile.absolutePath)

    return cascadeClassifier
}

private fun detectFaces(image: Mat, cascadeClassifier: CascadeClassifier): List<Rect> {
    val grayImage = convertToGray(image)

    val faces = MatOfRect()
    cascadeClassifier.detectMultiScale(grayImage, faces)

    return faces.toList()
}

private fun resizeImage(image: Mat, newSize: Size): Mat {
    val resizedImage = Mat()
    Imgproc.resize(image, resizedImage, newSize)
    return resizedImage
}

private fun calculateFaceMatchAccuracy(image1: Mat, image2: Mat): Double {
    val size = Size(100.0, 100.0)
    val resizedImage1 = resizeImage(image1, size)
    val resizedImage2 = resizeImage(image2, size)

    val grayImage1 = convertToGray(resizedImage1)
    val grayImage2 = convertToGray(resizedImage2)

    val floatImage1 = Mat()
    grayImage1.convertTo(floatImage1, CvType.CV_32F)
    val floatImage2 = Mat()
    grayImage2.convertTo(floatImage2, CvType.CV_32F)

    val diff = Mat()
    Core.subtract(floatImage1, floatImage2, diff)
    val norm = Core.norm(diff)

    return (1.0 - (norm / (size.width * size.height))) * 100.0
}