package com.example.villa.ui.view.pelanggan

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
import com.example.villa.ui.viewmodel.pelanggan.DetailPelangganUiState
import com.example.villa.ui.viewmodel.pelanggan.DetailPelangganViewModel

object DestinasiDetaiPelanggan : DestinasiNavigasi{
    override val route = "detailpelanggan"
    override val titleRes = "Detail Pelanggan"
    const val ID_Pelanggan = "id_pelanggan"
    val routeWithArg = "$route/{$ID_Pelanggan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPelangganScreen(
    onEditClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.pelangganUiState

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetaiPelanggan.titleRes) },
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
                    is DetailPelangganUiState.Loading -> CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is DetailPelangganUiState.Error -> Text(
                        text = "Gagal memuat data.",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    is DetailPelangganUiState.Success -> {
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
                                    Text(text = "Id Pelanggan: ${pelanggan.id_pelanggan}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "Nama Pelanggan: ${pelanggan.nama_pelanggan}", style = MaterialTheme.typography.bodyLarge)
                                    Text(text = "No HP: ${pelanggan.no_hp}", style = MaterialTheme.typography.bodyLarge)
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = {onEditClick (pelanggan.id_pelanggan)}) {
                                    Text("Edit Data")
                                }
                                Button(onClick = {
                                    viewModel.deletePelanggan(
                                        id_pelanggan = pelanggan.id_pelanggan,
                                        onSuccess = {
                                            onBackClick()
                                        },
                                        onError = {}
                                    )
                                }) {
                                    Text("Hapus Pelanggan")
                                }
                            }

                        }
                    }
                }
            }
        }
    )
}