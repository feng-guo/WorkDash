package com.example.workdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.workdash.screen.MainScreen
import com.example.workdash.ui.theme.WorkDashTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkDashTheme {

//                navController = rememberNavController()
//                SetupNavGraph(navController = navController)
                MainScreen()
            }
        }
    }
}
