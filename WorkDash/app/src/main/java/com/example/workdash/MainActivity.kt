package com.example.workdash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.example.workdash.screen.MainScreen
import com.example.workdash.ui.theme.WorkDashTheme
import android.content.Intent
import android.widget.Button

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val register = findViewById<Button>(R.id.register)
        val login = findViewById<Button>(R.id.login)

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContent {
            WorkDashTheme {

//                navController = rememberNavController()
//                SetupNavGraph(navController = navController)
                MainScreen()
            }
        }
    }
}
