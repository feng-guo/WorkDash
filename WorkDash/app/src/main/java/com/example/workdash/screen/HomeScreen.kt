package com.example.workdash.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.workdash.models.ScreenRoute

@Composable
fun HomeScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "This is Home Page",
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                navController.navigate(route = ScreenRoute.Login.route) {
                    popUpTo(ScreenRoute.Login.route){
                        inclusive = true
                    }
                }
            }
        ) {
            Text(
                text = "Go to Login Page",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

    }
}