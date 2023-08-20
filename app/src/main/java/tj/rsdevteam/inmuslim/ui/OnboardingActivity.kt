package tj.rsdevteam.inmuslim.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tj.rsdevteam.inmuslim.ui.common.Route
import tj.rsdevteam.inmuslim.ui.region.RegionScreen
import tj.rsdevteam.inmuslim.ui.theme.InmuslimTheme
import tj.rsdevteam.inmuslim.utils.findActivity

/**
 * Created by Rustam Safarov on 8/20/23.
 * github.com/rustamsafarovrs
 */

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InmuslimTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnboardingNavigation(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun OnboardingNavigation(navHostController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navHostController, startDestination = Route.REGIONS) {
        composable(Route.REGIONS) {
            RegionScreen {
                Intent(context, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(this)
                }
                context.findActivity().finish()
            }
        }
    }
}