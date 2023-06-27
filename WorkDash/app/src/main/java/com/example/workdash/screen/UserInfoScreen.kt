package com.example.workdash.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.workdash.routes.ScreenRoute

@Composable
fun UserInfo(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                navController.navigate(route = ScreenRoute.Home.route) { // NOTE: change this routing to general postings
                    popUpTo(ScreenRoute.Home.route){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.height(40.dp).width(100.dp)
        ) {
            Text("Log Out")
        }
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    UserInfo(navController = rememberNavController())
}