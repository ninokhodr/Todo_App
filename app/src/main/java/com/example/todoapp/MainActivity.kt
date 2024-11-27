package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.theme.TodoAppTheme

// Huvudaktiviteten för Todo-appen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sätter innehållet för appen med hjälp av Compose
        setContent {
            TodoAppTheme {
                AppNavHost() // Startar navigationen
            }
        }
    }
}

// Definierar navigationen för appen med hjälp av NavHost
@Composable
fun AppNavHost() {
    // Skapar en NavController för att hantera navigering mellan skärmar
    val navController: NavHostController = rememberNavController()

    // NavHost håller alla skärmar för att appen ska kunna navigera mellan dem
    NavHost(
        navController = navController,
        startDestination = "homeFragment" // Definierar startskärmen
    ) {
        // Ruta för hemskärmen, detaljskärmen och inställningskärmen
        composable("homeFragment") { HomeFragment(navController) }
        composable("detailsFragment") { DetailsFragment(navController) }
        composable("settingsFragment") { SettingsFragment(navController) }
    }
}
