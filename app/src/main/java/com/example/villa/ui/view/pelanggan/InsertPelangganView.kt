package com.example.villa.ui.view.pelanggan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.pelanggan.InsertPelangganUiEvent
import com.example.villa.ui.viewmodel.pelanggan.InsertPelangganUiState
import com.example.villa.ui.viewmodel.pelanggan.InsertPelangganViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPelanggan : DestinasiNavigasi{
    override val route = "insert_pelanggan"
    override val titleRes = "insert pelanggan"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPelangganScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){
            innerPadding ->
        EntryBodyPelanggan(
            insertUiState = viewModel.uiPelangganState,
            onPelangganValueChange = viewModel::updateInsertPelangganState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.insertPlg()
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
fun EntryBodyPelanggan(

    insertUiState: InsertPelangganUiState,
    onPelangganValueChange: (InsertPelangganUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertPelangganUiEvent = insertUiState.insertPelangganUiEvent,
            onValueChange = onPelangganValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertPelangganUiEvent: InsertPelangganUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPelangganUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertPelangganUiEvent.nama_pelanggan,
            onValueChange = {onValueChange(insertPelangganUiEvent.copy(nama_pelanggan = it))},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPelangganUiEvent.no_hp,
            onValueChange = {onValueChange(insertPelangganUiEvent.copy(no_hp = it))},
            label = { Text("No Hp") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}