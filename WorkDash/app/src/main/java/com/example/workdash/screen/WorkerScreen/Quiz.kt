package com.example.workdash.screen.WorkerScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import com.example.workdash.routes.ScreenRoute


@OptIn(ExperimentalComposeUiApi::class)
@Composable

fun Quiz(navController: NavController) {

    val questions = listOf(
        "Its okay to be 5 minutes late to work as long as no one notices",
        "If you see a fight break out in the workplace between customers, you should call the police",
        "Its okay to go home 10 minutes before your shift ends"
    )

    var selectedOptions by remember { mutableStateOf(listOf<Boolean?>(null, null, null)) }
    val contextForToast = LocalContext.current
    var enabled by remember { mutableStateOf(true) }
//    var contextForToast =
//    var enabled =

    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        questions.forEachIndexed { index, question ->
            Column(
                modifier = Modifier.clickable {
                    val currentOption = selectedOptions[index]
                    selectedOptions = selectedOptions.toMutableList().apply {
                        this[index] = if (currentOption == null) true else !currentOption
                    }
                }
            ) {

                Text(
                    text = question,
                    style = MaterialTheme.typography.h1,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {


                    OptionBox(selectedOptions[index] == true) {
                        selectedOptions = selectedOptions.toMutableList().apply {
                            this[index] = true
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "True",
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(16.dp))


                    OptionBox(selectedOptions[index] == false) {
                        selectedOptions = selectedOptions.toMutableList().apply {
                            this[index] = false
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "False",
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                Toast.makeText(contextForToast, "Passed", Toast.LENGTH_SHORT).show()
                enabled = false
                navController.navigate(ScreenRoute.UserDetailsWorker.route) {
                    popUpTo(ScreenRoute.UserDetailsWorker.route) {
                        inclusive = true
                    }
                }
            },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Done", color = Color.White)
        }

    }
}

@Composable
fun OptionBox(isSelected: Boolean, onClick: () -> Unit) {

    Checkbox(
        checked = isSelected,
        onCheckedChange = { isChecked -> onClick() }
    )
}
