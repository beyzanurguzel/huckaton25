package com.example.myapplication.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.presentation.screens.*
import com.example.myapplication.presentation.viewmodel.SurveyViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: SurveyViewModel = viewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(240.dp)) {
                Spacer(Modifier.height(24.dp))
                Text(
                    "Menü",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()

                // artık Composable
                DrawerItem("Ana Sayfa", "home") {
                    scope.launch { drawerState.close() }
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
                DrawerItem("İstatistikler", "stats") {
                    scope.launch { drawerState.close() }
                    navController.navigate("stats") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
                DrawerItem("Ayarlar", "settings") {
                    scope.launch { drawerState.close() }
                    navController.navigate("settings") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
                // Mevcut DrawerItem’ların altına ekle
                DrawerItem("Fatura Tahmini", "bill_prediction") {
                    scope.launch { drawerState.close() }
                    navController.navigate("bill_prediction") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
                // DrawerItem ekle
                DrawerItem("Sıralamalar", "rankings") {
                    scope.launch { drawerState.close() }
                    navController.navigate("rankings") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }

                DrawerItem("Bitki", "plant") {
                    scope.launch { drawerState.close() }
                    navController.navigate("plant") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }

                DrawerItem("Görevler", "tasks") {
                    scope.launch { drawerState.close() }
                    navController.navigate("tasks") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }

                DrawerItem("Ev Taraması & Öneri", "energy_audit") {
                    scope.launch { drawerState.close() }
                    navController.navigate("energy_audit") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }




            }
        }
    ) {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "auth",
                modifier = Modifier.padding(innerPadding)
            ) {

                composable("auth") {
                    AuthScreen(navController = navController)
                }

                composable("selection") {
                    SelectionScreen(
                        navController = navController,
                        openDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable(
                    "login/{type}",
                    arguments = listOf(navArgument("type") { type = NavType.StringType })
                ) { back ->
                    val type = back.arguments?.getString("type") ?: "individual"
                    LoginScreen(navController, type)
                }
                composable(
                    "survey_step/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { back ->
                    val id = back.arguments?.getInt("id") ?: 1
                    SurveyStepScreen(navController, id, viewModel)
                }
                composable("survey_result") {
                    SurveyResultScreen(navController, viewModel)
                }
                composable("character") {
                    CharacterScreen(navController, viewModel)
                }
                composable("home") {
                    HomeScreen(
                        navController = navController,
                        viewModel = viewModel,
                        openDrawer = { scope.launch { drawerState.open() } }
                    )
                }
                composable("stats") {
                    StatsScreen(navController = navController)
                }
                composable("settings") {
                    SettingsScreen(navController = navController)
                }
                // … NavHost içinde uygun noktaya ekle …
                composable("bill_prediction") {
                    BillPredictionScreen(navController = navController)
                }

                composable("rankings") {
                    RankingsScreen(navController = navController)
                }

                composable("plant") {
                    PlantScreen(navController = navController)
                }

                composable("tasks") {
                    TasksScreen(
                        navController = navController,
                        openDrawer    = { scope.launch { drawerState.open() } },
                        taskViewModel = viewModel()
                    )
                }

                composable("energy_audit") {
                    EnergyAuditScreen()
                }



            }
        }
    }
}



// DrawerItem artık Composable
@Composable
private fun DrawerItem(
    label: String,
    route: String,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
