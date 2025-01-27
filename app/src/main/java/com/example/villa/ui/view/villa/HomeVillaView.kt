package com.example.villa.ui.view.villa

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.R
import com.example.villa.model.Villa
import com.example.villa.ui.customwidget.BottomMenuBar
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.villa.HomeVillaUiState
import com.example.villa.ui.viewmodel.villa.HomeVillaViewModel

object DestinasiHomeVilla : DestinasiNavigasi {
    override val route = "homevilla"
    override val titleRes = "Home Villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToInsertVilla: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onHome: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onReservasi: () -> Unit ,
    viewModel: HomeVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getVilla() },
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
                onClick = navigateToInsertVilla,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Villa")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homeVillaUiState = viewModel.villaUiState,
            retryAction = { viewModel.getVilla() },
            onDetailClick = onDetailClick,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun HomeStatus(
    homeVillaUiState: HomeVillaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
) {
    when (homeVillaUiState) {
        is HomeVillaUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is HomeVillaUiState.Success -> {
            if (homeVillaUiState.villa.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Villa")
                }
            } else {
                VillaLayout(
                    villa = homeVillaUiState.villa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                )
            }
        }

        is HomeVillaUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connecction_error),
            contentDescription = stringResource(R.string.loading)
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
fun VillaLayout(
    villa: List<Villa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(villa) { villa ->
            VillaCard(
                villa = villa,
                onDetailClick = onDetailClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VillaCard(
    villa: Villa,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
) {
    Card(
        onClick = { onDetailClick(villa.id_villa) },
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
                    text = villa.nama_villa,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Kamar Tersedia: ${villa.kamar_tersedia}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Text(
                text = villa.alamat,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

