package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Quiz(navController: NavController) {
    // Create a list of three questions and their corresponding options (true or false)
    val questions = listOf(
        "Its okay to be 5 minutes late to work",
        "If you see a fight break out in the workplace between customers, you should call the police",
        "Its okay to go home 30 minutes before your shift ends"
    )

    // Create a mutable list to store the user's selected options
    var selectedOptions by remember { mutableStateOf(listOf<Boolean?>(null, null, null)) }

    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Loop through the questions and display them with options (true or false)
        questions.forEachIndexed { index, question ->
            Column(
                modifier = Modifier.clickable {
                    // Toggle the option when the row is clicked
                    val currentOption = selectedOptions[index]
                    selectedOptions = selectedOptions.toMutableList().apply {
                        this[index] = if (currentOption == null) true else !currentOption
                    }
                }
            ) {
                // Display the question
                Text(
                    text = question,
                    style = MaterialTheme.typography.h1,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Display the options (True and False) with small checkboxes beside each option
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    OptionBox(selectedOptions[index] == true) {
                        // Set the option to true when the True box is clicked
                        selectedOptions = selectedOptions.toMutableList().apply {
                            this[index] = true
                        }
                    }
                    Text(text = "True", modifier = Modifier.padding(start = 8.dp), textAlign = TextAlign.Center)

                    OptionBox(selectedOptions[index] == false) {
                        // Set the option to false when the False box is clicked
                        selectedOptions = selectedOptions.toMutableList().apply {
                            this[index] = false
                        }
                    }
                    Text(text = "False", modifier = Modifier.padding(start = 8.dp), textAlign = TextAlign.Center)
                }
            }
        }
    }
}


@Composable
fun OptionBox(isSelected: Boolean, onClick: () -> Unit) {
    // Small box to represent the option (True or False) with a click listener
    Checkbox(
        checked = isSelected,
        onCheckedChange = { isChecked -> onClick() }
    )
}
