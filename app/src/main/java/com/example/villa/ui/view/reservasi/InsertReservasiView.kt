package com.example.villa.ui.view.reservasi

import android.app.DatePickerDialog
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.navigation.DropDown
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.reservasi.InsertReservasiUiEvent
import com.example.villa.ui.viewmodel.reservasi.InsertReservasiUiState
import com.example.villa.ui.viewmodel.reservasi.InsertReservasiViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DestinasiInsertReservasi : DestinasiNavigasi {
    override val route = "insertreservasi"
    override val titleRes = "Insert Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertReservasiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertReservasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiReservasiState

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            delay(1000)
            navigateBack()
        }
    }
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertReservasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyReservasi(
            insertUiState = viewModel.uiReservasiState,
            onReservasiValueChange = viewModel::updateInsertReservasiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertRsv(navigateBack)

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
fun EntryBodyReservasi(
    insertUiState: InsertReservasiUiState,
    onReservasiValueChange: (InsertReservasiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputReservasi(
            insertReservasiUiEvent = insertUiState.insertReservasiUiEvent,
            villaOptions = insertUiState.villaOptions,
            pelangganOptions = insertUiState.pelangganOptions,
            onValueChange = onReservasiValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        insertUiState.errorMessage?.let { errorMessage ->
            Text(
                text = errorMessage,
                color = androidx.compose.ui.graphics.Color.Red,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
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
fun FormInputReservasi(
    insertReservasiUiEvent: InsertReservasiUiEvent,
    villaOptions: List<com.example.villa.ui.navigation.DropdownOption>,
    pelangganOptions: List<com.example.villa.ui.navigation.DropdownOption>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertReservasiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val context = LocalContext.current
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    val calendar = Calendar.getInstance()

    // State for DatePicker
    val checkInDate = remember { mutableStateOf(insertReservasiUiEvent.check_in) }
    val checkOutDate = remember { mutableStateOf(insertReservasiUiEvent.check_out) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Check-In DatePicker
        Button(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        val formattedDate = dateFormatter.format(calendar.time)
                        checkInDate.value = formattedDate
                        onValueChange(insertReservasiUiEvent.copy(check_in = formattedDate))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (checkInDate.value.isEmpty()) "Select Check-In Date" else "Check-In: ${checkInDate.value}")
        }

        // Check-Out DatePicker
        Button(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        val formattedDate = dateFormatter.format(calendar.time)
                        checkOutDate.value = formattedDate
                        onValueChange(insertReservasiUiEvent.copy(check_out = formattedDate))
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (checkOutDate.value.isEmpty()) "Select Check-Out Date" else "Check-Out: ${checkOutDate.value}")
        }

        // Jumlah Kamar Field
        OutlinedTextField(
            value = insertReservasiUiEvent.jumlah_kamar.toString(),
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                    onValueChange(insertReservasiUiEvent.copy(jumlah_kamar = newValue.toIntOrNull() ?: 0))
                }
            },
            label = { Text("Jumlah Kamar") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Villa Dropdown
        DropDown(
            selectedValue = villaOptions.find { it.id == insertReservasiUiEvent.id_villa },
            options = villaOptions,
            label = "Villa",
            onValueChangedEvent = { selected ->
                onValueChange(insertReservasiUiEvent.copy(id_villa = selected.id))
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Pelanggan Dropdown
        DropDown(
            selectedValue = pelangganOptions.find { it.id == insertReservasiUiEvent.id_pelanggan },
            options = pelangganOptions,
            label = "Pelanggan",
            onValueChangedEvent = { selected ->
                onValueChange(insertReservasiUiEvent.copy(id_pelanggan = selected.id))
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
