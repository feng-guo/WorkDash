package com.example.workdash.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workdash.BottomNavGraph
import com.example.workdash.routes.BottomBarScreen
import com.example.workdash.routes.ScreenRoute
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomBar(navController = navController) }
    ){
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.UserInfo,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {

        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any{
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            val currentUser = getUser()
            if (screen.title == "Home" && currentUser != null) {
                redirectBaseOnCurrentUser(currentUser.uid) { route ->
                    Log.d("route", route)
                    navController.navigate(route) {
                        popUpTo(route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }


    )
}

fun getUser(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

fun redirectBaseOnCurrentUser(userId: String, callback: (String) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    val query = database.child("userProfile").orderByChild("uid").equalTo(userId)

    // Read the data from the database
    query.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            var route = ""
            for (snapshot in dataSnapshot.children) {
                // Access the value of isWorker from each matching employer profile
                val worker = snapshot.child("worker").getValue(Boolean::class.java)
                route = if (worker == true) {
                    ScreenRoute.ListOfJobs.route
                } else {
                    ScreenRoute.CurrentJobPostsEmployer.route
                }
                Log.d("get me that route", route)
            }
            // Call the callback with the route once the query is complete
            callback.invoke(route)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Handle db error if needed
        }
    })
}