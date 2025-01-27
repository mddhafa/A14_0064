package com.example.villa.ui.view.reservasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.reservasi.DetailReservasiUiState
import com.example.villa.ui.viewmodel.reservasi.DetailReservasiViewModel

object DestinasiDetailReservasi : DestinasiNavigasi {
    override val route = "detailreservasi"
    override val titleRes = "Detail Reservasi"
    const val ID_Reservasi = "id_reservasi"
    val routeWithArg = "$route/{$ID_Reservasi}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailReservasiScreen(
    onEditClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.reservasiDetailUiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailReservasi.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is DetailReservasiUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is DetailReservasiUiState.Error -> Text(
                        text = "Gagal memuat data.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is DetailReservasiUiState.Success -> {
                        val reservasi = uiState.reservasi
                        val villa = uiState.villa
                        val pelanggan = uiState.pelanggan
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(text = "Id Reservasi: ${reservasi.id_reservasi}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Nama Villa: ${villa.nama_villa}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Nama Pelanggan: ${pelanggan.nama_pelanggan}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Check in: ${reservasi.check_in}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Check out: ${reservasi.check_out}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Jumlah Kamar: ${reservasi.jumlah_kamar}", style = MaterialTheme.typography.bodyLarge )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = {onEditClick (villa.id_villa)}) {
                                    Text("Edit Data")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}