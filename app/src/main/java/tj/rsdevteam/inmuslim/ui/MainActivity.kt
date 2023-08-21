package tj.rsdevteam.inmuslim.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.rsdevteam.inmuslim.ui.common.Route
import tj.rsdevteam.inmuslim.ui.home.HomeScreen
import tj.rsdevteam.inmuslim.ui.settings.SettingsScreen
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InmuslimTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Route.HOME) {
        composable(Route.HOME) {
            HomeScreen(navigateToSettings = {
                navHostController.navigate(Route.SETTINGS)
            })
        }
        composable(Route.SETTINGS) {
            SettingsScreen(popBackStack = {
                navHostController.popBackStack()
            })
        }
    }
}
