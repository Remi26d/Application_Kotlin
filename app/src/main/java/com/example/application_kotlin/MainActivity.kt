package com.example.application_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.application_kotlin.presentation.screens.HomeScreen
import com.example.application_kotlin.presentation.screens.DetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberNavController()

            MaterialTheme {
                NavHost(navController = nav, startDestination = "home") {

                    composable("home") {
                        HomeScreen(onCountrySelected = { code ->
                            nav.navigate("detail/$code")
                        })
                    }

                    composable(
                        "detail/{code}",
                        arguments = listOf(navArgument("code") {
                            type = NavType.StringType
                        })
                    ) { entry ->
                        val code = entry.arguments?.getString("code") ?: ""
                        DetailScreen(code)
                    }
                }
            }
        }
    }
}
