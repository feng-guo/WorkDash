package com.example.workdash.screen.WorkerScreen


import android.app.TimePickerDialog
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Calendar
import kotlin.math.roundToInt

@Composable
fun UserDetailsWorkerScreen(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableStateOf(0f) }
    val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val fromTime = remember { mutableStateOf("") }

    val mTimePickerDialog1 = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            fromTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, true
    )

    val toTime = remember { mutableStateOf("") }

    val mTimePickerDialog2 = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            toTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, true
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
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

        Text(text = "Salary: $sliderPosition CAD")
        Slider(value = sliderPosition, onValueChange = { sliderPosition = it.roundToInt().toFloat() }, valueRange = 0f..50f)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Working Days/hours")
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            daysOfWeek.forEach { day ->
                DayOfWeekItem(day)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxSize().padding(4.dp).align(Alignment.CenterHorizontally)) {
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

        Button(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Sign Up")
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
