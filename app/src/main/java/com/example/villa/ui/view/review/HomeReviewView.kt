package com.example.villa.ui.view.review


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
import com.example.villa.model.Pelanggan
import com.example.villa.model.Reservasi
import com.example.villa.model.Review
import com.example.villa.ui.customwidget.BottomMenuBar
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.review.HomeReviewUiState
import com.example.villa.ui.viewmodel.review.HomeReviewViewModel

object DestinasiHomeReview : DestinasiNavigasi {
    override val route = "homereview"
    override val titleRes = "Home Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeReviewScreen(
    navigateToInsertReview: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (Int) -> Unit,
    onVilla: () -> Unit = {},
    onReview: () -> Unit = {},
    onHome: () -> Unit = {},
    onPelanggan: () -> Unit = {},
    onReservasi: () -> Unit = {},
    viewModel: HomeReviewViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Review",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getReview() },
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
                onClick = navigateToInsertReview,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Review")
            }
        }
    ) { innerPadding ->
        HomeReviewStatus(
            reviewUiState = viewModel.reviewUiState,
            retryAction = { viewModel.getReview() },
            modifier = Modifier.padding(innerPadding),
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomeReviewStatus(
    reviewUiState: HomeReviewUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onEditClick: (Int) -> Unit
) {
    when (reviewUiState) {
        is HomeReviewUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is HomeReviewUiState.Success -> {
            if (reviewUiState.review.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Review")
                }
            } else {
                ReviewList(
                    reviews = reviewUiState.review,
                    reservasi = reviewUiState.reservasi,
                    pelanggan = reviewUiState.pelangan,
                    modifier = modifier.fillMaxWidth(),
                    onEditClick = onEditClick
                )
            }
        }

        is HomeReviewUiState.Error -> {
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
fun ReviewList(
    reviews: List<Review>,
    reservasi: List<Reservasi>,
    pelanggan: List<Pelanggan>,
    modifier: Modifier = Modifier,
    onEditClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(reviews) { review ->
            val matchedReservasi = reservasi.find { it.id_reservasi == review.id_reservasi }
            val matchedPelanggan = matchedReservasi?.let { reservasi ->
                pelanggan.find { it.id_pelanggan == reservasi.id_pelanggan }
            }
            ReviewCard(
                review = review,
                reservasi = matchedReservasi,
                pelanggan = matchedPelanggan,
                onEditClick = onEditClick
            )
        }
    }
}


@Composable
fun ReviewCard(
    review: Review,
    reservasi: Reservasi?,
    pelanggan: Pelanggan?,
    modifier: Modifier = Modifier,
    onEditClick: (Int) -> Unit
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
                    text = review.id_review.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onEditClick(review.id_review) }) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Detail Pelanggan",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            // Menampilkan nama pelanggan dari reservasi
            Text(
                text = "Reservasi: ${reservasi?.id_reservasi ?: "Tidak ditemukan"}",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Pelanggan: ${pelanggan?.nama_pelanggan?: "Tidak ditemukan"}",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Komentar: ${review.komentar ?: "Tidak ada komentar"}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Nilai: ${review.nilai}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
