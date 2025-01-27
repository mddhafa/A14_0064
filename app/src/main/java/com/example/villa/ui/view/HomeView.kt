package com.example.villa.ui.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.height
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.villa.R
import androidx.compose.material3.BottomAppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.customwidget.Navbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import com.example.villa.model.Villa
import com.example.villa.ui.customwidget.BottomMenuBar
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.villa.HomeVillaUiState
import com.example.villa.ui.viewmodel.villa.HomeVillaViewModel

object DestinasiHome : DestinasiNavigasi {
    override val route = "Home"
    override val titleRes = "home"
}
@Composable
fun HomeTokoView(
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onHome: () -> Unit = {},
    onReservasi: () -> Unit = {},
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            Navbar(
                nama = "Ayo Reservasi Villa di Sini",
                showBackButton = false
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
        }
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
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(villa) { villa ->
            VillaCard(
                villa = villa,
                onDetailClick = onDetailClick
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
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Villa Image Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF56CCF2), Color(0xFF2F80ED))
                        )
                    ),
            ) {
                Text(
                    text = villa.nama_villa,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Villa Details
            Text(
                text = "Alamat: ${villa.alamat}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Kamar Tersedia: ${villa.kamar_tersedia}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
