import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mamaocho.taller2.ui.theme.Taller2Theme
import com.mamaocho.taller2.MainViewModel
import com.mamaocho.taller2.ui.screens.MainScreen
import com.mamaocho.taller2.ui.screens.FamilyRegistrationScreen
import com.mamaocho.taller2.ui.screens.LocationRegistrationScreen

sealed class GRAPH(val graph: String) {
    data object MAIN: GRAPH(graph = "MAIN")
}

sealed class MainScreens(val route: String) {
    data object MainScreen: MainScreens(route = "main_screen")
    data object FamilyRegistrationScreen: MainScreens(route = "family_registration_screen")
    data object PersonRegistrationScreen: MainScreens(route = "person_registration_screen")
    data object LocationRegistrationScreen: MainScreens(route = "location_registration_screen")
}

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    Taller2Theme {
        NavHost(
            navController = navController,
            startDestination = MainScreens.MainScreen.route
        ) {
            composable(MainScreens.MainScreen.route) {
                MainScreen(
                    viewModel = viewModel,
                    onAddFamilyClick = { navController.navigate(MainScreens.FamilyRegistrationScreen.route) },
                    onAddPersonClick = { navController.navigate(MainScreens.PersonRegistrationScreen.route) }
                )
            }
            composable(MainScreens.FamilyRegistrationScreen.route) {
                FamilyRegistrationScreen(
                    viewModel = viewModel,
                    onRegisterClick = { navController.navigate(MainScreens.MainScreen.route) }
                )
            }
            composable(MainScreens.PersonRegistrationScreen.route) {
                PersonRegistrationScreen(
                    viewModel = viewModel,
                    onRegisterClick = { navController.navigate(MainScreens.MainScreen.route) },
                    onLocationClick = { navController.navigate(MainScreens.LocationRegistrationScreen.route) },
                    familyId = 1 // You should pass the actual familyId
                )
            }
            composable(MainScreens.LocationRegistrationScreen.route) {
                LocationRegistrationScreen(
                    viewModel = viewModel,
                )
            }
        }
    }
}
