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
fun LoginScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                navController.navigate(route = ScreenRoute.UserInfo.route) {
                    popUpTo(ScreenRoute.UserInfo.route){
                        inclusive = true
                    }
                }
            }
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

//@Preview
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(
//        navController = rememberNavController()
//    )
//}