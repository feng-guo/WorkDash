package com.example.workdash.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    //onLoginClicked: (emailOrPhone: String, password: String) -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "WorkDash",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { it ->  emailOrPhone = it},
            label = { Text("Email/Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Forgot password?", modifier = Modifier.clickable { /* Handle click event */ })
            Spacer(modifier = Modifier.width(16.dp))
            Text("Sign Up", modifier = Modifier.clickable { /* Handle click event */ })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
            //onLoginClicked(emailOrPhone, password)

        }) {
            Text("Log In")
        }
    }

}

@Preview
@Composable
fun LoginPagePreview() {
    LoginScreen(
        navController = rememberNavController(),
//        onLoginClicked = { emailOrPhone, password ->
//        // Perform login logic here
//    }
    )
}