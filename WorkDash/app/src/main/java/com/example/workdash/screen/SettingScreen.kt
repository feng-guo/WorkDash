package com.example.workdash.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun SettingScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "This is setting page",
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(
//        navController = rememberNavController()
//    )
//}