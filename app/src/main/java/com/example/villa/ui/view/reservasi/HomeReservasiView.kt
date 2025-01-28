package com.example.villa.ui.view.reservasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.R
import com.example.villa.model.Reservasi
import com.example.villa.model.Villa
import com.example.villa.ui.customwidget.BottomMenuBar
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.reservasi.HomeReservasiUiState
import com.example.villa.ui.viewmodel.reservasi.HomeReservasiViewModel

object DestinasiHomeReservasi : DestinasiNavigasi {
    override val route = "homereservasi"
    override val titleRes = "Home Reservasi"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeReservasiScreen(
    navigateToInsertReservasi: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onHome: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onReservasi: () -> Unit = {},
    viewModel: HomeReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getReservasi()
                },
                navigateUp = navigateBack
            )
        },
        bottomBar = {
            BottomMenuBar(
                onVilla = onVilla,
                onReview = onReview,
                onHome = onHome,
                onPelanggan = onPelanggan,
                onReservasi = onReservasi
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsertReservasi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF00BFFF),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Villa")
            }
        },
    ) { innerPadding ->
        HomeReservasiStatus(
            homeReservasiUiState = viewModel.reservasiUiState,
            retryAction = { viewModel.getReservasi() },
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteReservasi(it.id_reservasi)
                viewModel.getReservasi()
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun HomeReservasiStatus(
    homeReservasiUiState: HomeReservasiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    when (homeReservasiUiState) {
        is HomeReservasiUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is HomeReservasiUiState.Success -> {
            if (homeReservasiUiState.reservasi.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Reservasi")
                }
            } else {
                ReservasiLayout(
                    reservasi = homeReservasiUiState.reservasi,
                    villa = homeReservasiUiState.villa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }

        is HomeReservasiUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}


/**
 * The home screen displaying the loading message.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}


/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connecction_error), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ReservasiLayout(
    reservasi: List<Reservasi>,
    villa: List<Villa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reservasi) { reservasi ->
            val matchedVilla = villa.find { it.id_villa == reservasi.id_villa }
            ReservasiCard(
                reservasi = reservasi,
                villa = matchedVilla,
                onDetailClick = onDetailClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}



@Composable
fun ReservasiCard(
    reservasi: Reservasi,
    villa: Villa?,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Reservasi) -> Unit = {}
) {
    Card(
        onClick = { onDetailClick(reservasi.id_reservasi)},
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.key),
                    contentDescription = "",
                    modifier = modifier.size(25.dp).padding(end = 8.dp)
                )
                Text(
                    text = reservasi.id_reservasi.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(reservasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = null,
                    )
                }
            }
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.handkey),
                    contentDescription = "",
                    modifier = modifier.size(25.dp).padding(end = 8.dp)
                )
                Text(
                    text = "Villa: ${villa?.id_villa ?: "Tidak ditemukan"}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.homevilla),
                    contentDescription = "",
                    modifier = modifier.size(25.dp).padding(end = 8.dp)
                )
                Text(
                    text = "${villa?.nama_villa ?: "Tidak ditemukan"}",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.calendarclock),
                    contentDescription = "",
                    modifier = modifier.size(25.dp).padding(end = 8.dp)
                )
                Text(
                    text = "Check In: ${reservasi.check_in}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.door),
                    contentDescription = "",
                    modifier = modifier.size(25.dp).padding(end = 8.dp)
                )
                Text(
                    text = "${reservasi.jumlah_kamar.toString()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }


    }
}