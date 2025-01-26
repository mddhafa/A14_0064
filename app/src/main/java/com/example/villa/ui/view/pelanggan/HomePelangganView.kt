package com.example.villa.ui.view.pelanggan

import com.example.villa.model.Pelanggan
import com.example.villa.ui.viewmodel.pelanggan.HomePelangganUiState
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.R
import com.example.villa.ui.customwidget.BottomMenuBar
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.pelanggan.HomePelangganViewModel


object DestinasiHomePelanggan: DestinasiNavigasi {
    override val route = "homepelanggan"
    override val titleRes = "Home Pelanggan"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePelangganScreen(
    navigateToInsertPelanggan: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onHome: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onReservasi: () -> Unit = {},
    viewModel: HomePelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPelanggan()
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
                onClick = navigateToInsertPelanggan,
                shape = MaterialTheme.shapes.medium, modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Villa")
            }
        },
    ) { innerPadding ->
        HomePelangganStatus(
            homePelangganUiState = viewModel.pelangganUiState,
            retryAction = {viewModel.getPelanggan()},
            onDetailClick = onDetailClick,
            onDeleteClick = {},
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomePelangganStatus(
    homePelangganUiState: HomePelangganUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Pelanggan) -> Unit

) {
   when(homePelangganUiState) {
       is HomePelangganUiState.Loading -> {
           OnLoading(modifier = modifier.fillMaxSize())
       }

       is HomePelangganUiState.Success -> {
           if (homePelangganUiState.pelanggan.isEmpty()){
               Box(
                   modifier = modifier.fillMaxSize(),
                   contentAlignment = Alignment.Center
               ) {
                   Text(text = "Tidak ada data Pelanggan")
               }
           }else{
               PelangganLayout(
                   pelanggan = homePelangganUiState.pelanggan,
                   modifier = modifier.fillMaxWidth(),
                   onDetailClick = onDetailClick,
                   onDeleteClick = onDeleteClick
               )
           }
       }

       is HomePelangganUiState.Error -> {
           OnError(retryAction, modifier = modifier.fillMaxSize())
       }
   }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}


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
fun PelangganLayout(
    pelanggan: List<Pelanggan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Pelanggan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pelanggan) { pelanggan ->
            PelangganCard(
                pelanggan = pelanggan,
                onDetailClick = onDetailClick,
                onDeleteClick = {}
            )
        }
    }


}


@Composable
fun PelangganCard(
    pelanggan: Pelanggan,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: () -> Unit = {}
) {
    Card(
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
                Text(
                    text = "Id Pelanggan: ${pelanggan.id_pelanggan.toString()}",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDetailClick(pelanggan.id_pelanggan) }) {
                    Image(
                        painter = painterResource(id = R.drawable.daetail),
                        contentDescription = "Detail Pelanggan",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Text(
                text = pelanggan.nama_pelanggan,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = pelanggan.no_hp,
                style = MaterialTheme.typography.bodyMedium
            )
        }


    }
}