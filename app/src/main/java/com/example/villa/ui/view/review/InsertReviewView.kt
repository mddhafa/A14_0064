package com.example.villa.ui.view.review

import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.navigation.DropDown
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.review.InsertReviewUiEvent
import com.example.villa.ui.viewmodel.review.InsertReviewUiState
import com.example.villa.ui.viewmodel.review.InsertReviewViewModel
import kotlinx.coroutines.launch

object DestinasiInsertReview : DestinasiNavigasi {
    override val route = "insertreview"
    override val titleRes = "Insert Review"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReviewScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReviewViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Menunggu state untuk memuat data reservasi
    val uiState = viewModel.uiReviewState
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertReview.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyReview(
            insertUiState = uiState,
            onReviewValueChange = { viewModel.updateInsertReviewState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertRev()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyReview(
    insertUiState: InsertReviewUiState,
    onReviewValueChange: (InsertReviewUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Form input review
        FormInputReview(
            insertReviewUiEvent = insertUiState.insertReviewUiEvent,
            reservasiOptions = insertUiState.reservasiOptions,
            onValueChange = onReviewValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol simpan
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputReview(
    insertReviewUiEvent: InsertReviewUiEvent,
    reservasiOptions: List<com.example.villa.ui.navigation.DropdownOption>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertReviewUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val nilai = listOf("Sangat Puas", "Puas", "Biasa", "Tidak Puas", "Sangat tidak Puas")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Nilai")

        // Input Rating
        Column(modifier = Modifier.fillMaxWidth()) {
            nilai.forEach { rating ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = insertReviewUiEvent.nilai == rating,
                        onClick = {
                            onValueChange(insertReviewUiEvent.copy(nilai = rating))
                        },
                        enabled = enabled
                    )
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Input Komentar
        OutlinedTextField(
            value = insertReviewUiEvent.komentar,
            onValueChange = { onValueChange(insertReviewUiEvent.copy(komentar = it)) },
            label = { Text("Komentar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false
        )

        // Dropdown untuk Reservasi
        DropDown(
            selectedValue = reservasiOptions.find { it.id == insertReviewUiEvent.id_reservasi },
            options = reservasiOptions,
            label = "Reservasi",
            onValueChangedEvent = { selected ->
                onValueChange(insertReviewUiEvent.copy(id_reservasi = selected.id))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
