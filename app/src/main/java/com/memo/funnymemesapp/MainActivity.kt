package com.memo.funnymemesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.memo.funnymemesapp.screens.DetailScreen
import com.memo.funnymemesapp.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "Main Screen"
            ) {
                composable("Main Screen") {
                    MainScreen()
                }
                composable("Detail Screen") {
                    DetailScreen()
                }
            }
        }
    }
}

