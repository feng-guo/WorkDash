package com.example.workdash.screen.WorkerScreen

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
import java.util.Calendar
import kotlin.math.roundToInt

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
    val IDs = listOf("Passport", "Driver's Licence", "Health Card", "Employee ID")
    val checkedIDs = remember { mutableStateListOf<String>() }

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
            Text(text = "Salary: $sliderPosition CAD", style = MaterialTheme.typography.h6)
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                valueRange = 0f..50f
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(text = "Working Days/hours", style = MaterialTheme.typography.h6)
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    daysOfWeek.forEach { day ->
                        DayOfWeekItem(day)
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxSize().padding(4.dp)) {
                Button(
                    onClick = { mTimePickerDialog1.show() },
                    Modifier.padding(horizontal = 20.dp)
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
                    Modifier.padding(horizontal = 20.dp)
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

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    onClick = { },
                    Modifier.padding(horizontal = 20.dp)
                        .width(128.dp)
                        .height(40.dp)
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}


@Composable
fun DayOfWeekItem(day: String) {
    var isSelected by remember { mutableStateOf(false) }

    Button(
        onClick = { isSelected = !isSelected },
        modifier = Modifier.padding(horizontal = 2.dp)
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
}
