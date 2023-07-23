package com.example.workdash.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.workdash.routes.ID_ARG
import com.example.workdash.routes.ScreenRoute
import com.example.workdash.services.ReportService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Report(navController: NavController) {
    val contextForToast = LocalContext.current.applicationContext
    var typedText by remember { mutableStateOf(TextFieldValue()) }

    val navBackStackEntry = navController.currentBackStackEntry
    val id = navBackStackEntry?.arguments?.getString(ID_ARG) ?: ""

    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val database: DatabaseReference = FirebaseDatabase.getInstance().reference
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val query = database.child("userProfile").orderByChild("uid").equalTo(currentUserUid)

        val satisfiedText = remember { mutableStateOf("") }
        // Read the data from the database
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {

                    val isWorker = snapshot.child("worker").getValue(Boolean::class.java)
                    if (isWorker == true) {

                        satisfiedText.value = "Do you have any complaints regarding this job?"


                    }
                    else{

                        satisfiedText.value = "Do you have any complaints regarding this employee?"
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    contextForToast,
                    "Database Error: $databaseError",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        Text(


            text = satisfiedText.value,
            style = MaterialTheme.typography.h1,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Black),
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

                        keyboardController?.hide()
                    }
                ),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    ReportService.createReport(id, typedText.toString(),
                        satisfiedText.toString() == "Do you have any complaints regarding this employee?"
                    )
                    navController.navigate(route = ScreenRoute.Rating.passId(id)) {
                        popUpTo(ScreenRoute.Rating.passId(id)) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Report")
            }

            Button(
                onClick = {
                    navController.navigate(route = ScreenRoute.Rating.passId(id)) {
                        popUpTo(ScreenRoute.Rating.passId(id)) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Text(text = "Skip")
            }
        }
    }
}
