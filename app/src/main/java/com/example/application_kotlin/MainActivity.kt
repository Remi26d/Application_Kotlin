package com.example.application_kotlin

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.application_kotlin.presentation.screens.DetailScreen
import com.example.application_kotlin.presentation.screens.FavoritesScreen
import com.example.application_kotlin.presentation.screens.HomeScreen
import com.example.application_kotlin.ui.theme.Application_KotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Application_KotlinTheme {
                AppScaffold()
            }
        }
    }
}

/**
 * AppScaffold : racine de l'IHM (comme demandé par ton prof)
 */
@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    MaterialTheme {
        IHM(navController = navController)
    }
}

/**
 * IHM : gère la navigation entre les écrans
 */
@Composable
fun IHM(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Écran d'accueil : liste des pays
        composable("home") {
            HomeScreen(
                onCountrySelected = { code, name ->
                    val encodedName = Uri.encode(name)
                    navController.navigate("detail/$code/$encodedName")
                },
                onFavoritesClick = {
                    navController.navigate("favorites")
                }
            )
        }

        // Détail d'un pays (code + nom)
        composable(
            route = "detail/{code}/{name}",
            arguments = listOf(
                navArgument("code") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStack ->
            val code = backStack.arguments?.getString("code") ?: ""
            val encodedName = backStack.arguments?.getString("name") ?: ""
            val name = Uri.decode(encodedName)

            DetailScreen(
                code = code,
                name = name,
                navController = navController
            )
        }

        // Écran des favoris
        composable("favorites") {
            FavoritesScreen(navController = navController)
        }
    }
}
