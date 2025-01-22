package com.example.villa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.villa.ui.view.DestinasiHome
import com.example.villa.ui.view.villa.DestinasiHomeVilla
import com.example.villa.ui.view.villa.HomeScreen
import com.example.villa.ui.view.HomeTokoView
import com.example.villa.ui.view.villa.DestinasiDetailVilla
import com.example.villa.ui.view.villa.DestinasiInsertVilla
import com.example.villa.ui.view.villa.DestinasiUpdateVilla
import com.example.villa.ui.view.villa.DetailScreen
import com.example.villa.ui.view.villa.InsertVillaScreen
import com.example.villa.ui.view.villa.UpdateScreen

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(
            route= DestinasiHome.route
        ){
            HomeTokoView(
                onVilla = { navController.navigate(DestinasiHomeVilla.route) },
                )
        }
        composable(DestinasiHomeVilla.route) {
            HomeScreen(
                navigateToInsertVilla = { navController.navigate(DestinasiInsertVilla.route) },
                navigateBack = { navController.navigate(DestinasiHome.route)},
                onDetailClick = { id_villa ->
                    navController.navigate("${DestinasiDetailVilla.route}/$id_villa")
                    println("PengelolaHalaman: id_villa = $id_villa")
                }
            )
        }

    }
}