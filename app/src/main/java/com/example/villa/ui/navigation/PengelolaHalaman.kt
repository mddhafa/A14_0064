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
import com.example.villa.ui.view.pelanggan.DestinasiDetaiPelanggan
import com.example.villa.ui.view.pelanggan.DestinasiHomePelanggan
import com.example.villa.ui.view.pelanggan.DestinasiInsertPelanggan
import com.example.villa.ui.view.pelanggan.DestinasiUpdatePelanggan
import com.example.villa.ui.view.pelanggan.DetailPelangganScreen
import com.example.villa.ui.view.pelanggan.HomePelangganScreen
import com.example.villa.ui.view.pelanggan.InsertPelangganScreen
import com.example.villa.ui.view.pelanggan.UpdatePelangganScreen
import com.example.villa.ui.view.reservasi.DestinasiDetailReservasi
import com.example.villa.ui.view.reservasi.DestinasiHomeReservasi
import com.example.villa.ui.view.reservasi.DestinasiInsertReservasi
import com.example.villa.ui.view.reservasi.DestinasiUpdateReservasi
import com.example.villa.ui.view.reservasi.DetailReservasiScreen
import com.example.villa.ui.view.reservasi.HomeReservasiScreen
import com.example.villa.ui.view.reservasi.InsertReservasiScreen
import com.example.villa.ui.view.reservasi.UpdateReservasiScreen
import com.example.villa.ui.view.review.DestinasiHomeReview
import com.example.villa.ui.view.review.DestinasiInsertReview
import com.example.villa.ui.view.review.DestinasiUpdateReview
import com.example.villa.ui.view.review.HomeReviewScreen
import com.example.villa.ui.view.review.InsertReviewScreen
import com.example.villa.ui.view.review.UpdateReviewScreen
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
                onReservasi = { navController.navigate(DestinasiHomeReservasi.route)},
                onHome = { navController.navigate(DestinasiHome.route)},
                onPelanggan = {navController.navigate(DestinasiHomePelanggan.route)},
                onReview = {navController.navigate(DestinasiHomeReview.route)},
                onDetailClick = { id_villa ->
                    navController.navigate("${DestinasiDetailVilla.route}/$id_villa")
                    println("PengelolaHalaman: id_villa = $id_villa")
                }
            )
        }

        //Vila
        composable(DestinasiHomeVilla.route) {
            HomeScreen(
                navigateToInsertVilla = { navController.navigate(DestinasiInsertVilla.route) },
                navigateBack = { navController.navigate(DestinasiHome.route)},
                onDetailClick = { id_villa ->
                    navController.navigate("${DestinasiDetailVilla.route}/$id_villa")
                    println("PengelolaHalaman: id_villa = $id_villa")
                },
                onVilla = { navController.navigate(DestinasiHomeVilla.route) },
                onReservasi = { navController.navigate(DestinasiHomeReservasi.route)},
                onHome = { navController.navigate(DestinasiHome.route)},
                onPelanggan = {navController.navigate(DestinasiHomePelanggan.route)},
                onReview = {navController.navigate(DestinasiHomeReview.route)}
            )
        }
        composable(DestinasiInsertVilla.route){
            InsertVillaScreen(
                navigateBack = { navController.navigate(DestinasiHomeVilla.route){
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
                }
            )
        }
        composable(DestinasiDetailVilla.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailVilla.ID_Villa){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val id_villa = backStackEntry.arguments?.getInt(DestinasiDetailVilla.ID_Villa)
            id_villa?.let {
                DetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEditClick = { id_villa ->
                        println("DetailScreen: Navigating to ${DestinasiUpdateVilla.route}/$id_villa")
                        navController.navigate("${DestinasiUpdateVilla.route}/$id_villa")
                    },
                    navigateToInsertReservasi = {navController.navigate(DestinasiInsertReservasi.route)},
                    navigateBack = {navController.popBackStack()}
                )
            }
        }
        composable(
            DestinasiUpdateVilla.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateVilla.IDVilla) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_villa = backStackEntry.arguments?.getInt(DestinasiUpdateVilla.IDVilla)
            println("NavHost: Navigating to UpdateScreen with id_villa = $id_villa")
            id_villa?.let {
                UpdateScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeVilla.route) {
                            popUpTo(DestinasiHomeVilla.route) { inclusive = true }
                        }
                    },
                    modifier = modifier
                )
            }
        }

        //Pelanggan
        composable(DestinasiHomePelanggan.route){
            HomePelangganScreen(
                navigateBack = {navController.popBackStack()},
                navigateToInsertPelanggan = {navController.navigate(DestinasiInsertPelanggan.route)},
                onDetailClick = { id_pelanggan ->
                    navController.navigate("${DestinasiDetaiPelanggan.route}/$id_pelanggan")
                    println("PengelolaHalaman : id_pelanggan = $id_pelanggan")
                },
                onVilla = { navController.navigate(DestinasiHomeVilla.route) },
                onReservasi = { navController.navigate(DestinasiHomeReservasi.route)},
                onHome = { navController.navigate(DestinasiHome.route)},
                onPelanggan = {navController.navigate(DestinasiHomePelanggan.route)},
                onReview = {navController.navigate(DestinasiHomeReview.route)}
            )
        }
        composable(DestinasiDetaiPelanggan.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetaiPelanggan.ID_Pelanggan){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val id_pelanggan = backStackEntry.arguments?.getInt(DestinasiDetaiPelanggan.ID_Pelanggan)
            id_pelanggan?.let {
                DetailPelangganScreen(
                    onBackClick = { navController.popBackStack()},
                    onEditClick = {
                        id_pelanggan ->
                        println("DetailPelangganScreen: Navigating to ${DestinasiUpdatePelanggan.route}/$id_pelanggan")
                        navController.navigate("${DestinasiUpdatePelanggan.route}/$id_pelanggan")
                    },
                    navigateBack = {navController.popBackStack()}
                )
            }
        }
        composable(DestinasiInsertPelanggan.route){
            InsertPelangganScreen(
                navigateBack = {navController.popBackStack()}
            )
        }
        composable(DestinasiUpdatePelanggan.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePelanggan.ID_Pelanggan) { type = NavType.IntType}
            )
        ){ backStackEntry ->
            val id_pelanggan = backStackEntry.arguments?.getInt(DestinasiUpdatePelanggan.ID_Pelanggan)
            println("NavHost: Navigating to UpdateScreen with id_villa = $id_pelanggan")
            id_pelanggan.let {
                UpdatePelangganScreen(
                    onBack = {navController.popBackStack()},
                    onNavigate = {navController.navigate(DestinasiHomePelanggan.route) {
                            popUpTo(DestinasiHomePelanggan.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        //Reservasi
        composable(DestinasiHomeReservasi.route){
            HomeReservasiScreen(
                navigateBack = {navController.popBackStack()},
                navigateToInsertReservasi = {navController.navigate(DestinasiInsertReservasi.route)},
                onDetailClick = {id_reservasi ->
                    navController.navigate("${DestinasiDetailReservasi.route}/$id_reservasi")
                    println("PengelolaHalaman : id_reservasi = $id_reservasi")
                },
                onVilla = { navController.navigate(DestinasiHomeVilla.route) },
                onReservasi = { navController.navigate(DestinasiHomeReservasi.route)},
                onHome = { navController.navigate(DestinasiHome.route)},
                onPelanggan = {navController.navigate(DestinasiHomePelanggan.route)},
                onReview = {navController.navigate(DestinasiHomeReview.route)}
            )
        }
        composable(DestinasiDetailReservasi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailReservasi.ID_Reservasi){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val id_reservasi = backStackEntry.arguments?.getInt(DestinasiDetailReservasi.ID_Reservasi) ?: return@composable
            DetailReservasiScreen(
                onBackClick = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateReservasi.route}/$id_reservasi")
                },
                navigateBack = {navController.popBackStack()},
                )
        }
        composable(DestinasiInsertReservasi.route){
            InsertReservasiScreen(
                navigateBack = {navController.popBackStack()},
            )
        }
        composable(
            DestinasiUpdateReservasi.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateReservasi.ID_Reservasi){
                    type = NavType.IntType
                }
            )
        ){
            backStackEntry ->
            val id_reservasi = backStackEntry.arguments?.getInt(DestinasiUpdateReservasi.ID_Reservasi) ?: return@composable
            UpdateReservasiScreen(
                onNavigate = {
                    navController.navigate(DestinasiHomeReservasi.route) {
                        popUpTo(DestinasiHomeReservasi.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )

        }

        // Review
        composable(DestinasiHomeReview.route) {
            HomeReviewScreen(
                navigateBack = { navController.popBackStack() },
                navigateToInsertReview = { navController.navigate(DestinasiInsertReview.route) },
                onEditClick = { id_review ->
                    navController.navigate("${DestinasiUpdateReview.route}/$id_review")
                    println("PengelolaHalaman : id_review = $id_review")
                },
                onVilla = { navController.navigate(DestinasiHomeVilla.route) },
                onReservasi = { navController.navigate(DestinasiHomeReservasi.route) },
                onHome = { navController.navigate(DestinasiHome.route) },
                onPelanggan = { navController.navigate(DestinasiHomePelanggan.route) },
                onReview = { navController.navigate(DestinasiHomeReview.route) }
            )
        }

        composable(DestinasiInsertReview.route) {
            InsertReviewScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            DestinasiUpdateReview.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateReview.ID_Review) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id_review = backStackEntry.arguments?.getInt(DestinasiUpdateReview.ID_Review)
                ?: return@composable

            UpdateReviewScreen(
                onNavigate = {
                    navController.navigate(DestinasiHomeReview.route) {
                        popUpTo(DestinasiHomeReview.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
