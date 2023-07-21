package com.example.workdash.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.text.KeyboardType
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
//import java.time.format.TextStyle

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Report(navController: NavController) {

    val contextForToast = LocalContext.current.applicationContext

  //  var rating by remember { mutableStateOf(0) }
    var typedText by remember { mutableStateOf(TextFieldValue()) }


    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp),
    ) {
        Text(
            text = "Do you have any complaints regarding this job?",
            style = MaterialTheme.typography.h1,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // The box to type something
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current

            BasicTextField(
                value = typedText.text,
                onValueChange = { newValue: String -> typedText = typedText.copy(text = newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle text submission here, if needed
                        keyboardController?.hide()
                    }
                ),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black)
            )

        }
    }
}
