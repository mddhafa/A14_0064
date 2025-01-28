package com.example.villa.ui.view.villa

import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.villa.InsertVillaUiEvent
import com.example.villa.ui.viewmodel.villa.InsertVillaUiState
import com.example.villa.ui.viewmodel.villa.InsertVillaViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertVilla : DestinasiNavigasi {
    override val route = "insert_villa"
    override val titleRes = "insert villa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertVillaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertVillaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertVilla.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){
            innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiVillaState,
            onVillaValueChange = viewModel::updateInsertVillaState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.insertVla()
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
fun EntryBody(
    insertUiState: InsertVillaUiState,
    onVillaValueChange: (InsertVillaUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertVillaUiEvent = insertUiState.insertVillaUiEvent,
            onValueChange = onVillaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BFFF),
                contentColor = Color.White
            )
        ){
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertVillaUiEvent: InsertVillaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertVillaUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertVillaUiEvent.nama_villa,
            onValueChange = {onValueChange(insertVillaUiEvent.copy(nama_villa = it))},
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertVillaUiEvent.alamat,
            onValueChange = {onValueChange(insertVillaUiEvent.copy(alamat = it))},
            label = { Text("alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertVillaUiEvent.kamar_tersedia.toString(), // Mengubah nilai int menjadi string
            onValueChange = { newValue ->
                // Mengecek apakah input bisa diubah menjadi angka
                if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                    onValueChange(insertVillaUiEvent.copy(kamar_tersedia = newValue.toIntOrNull() ?: 0)) // Mengubah kembali ke int
                }
            },
            label = { Text("Kamar") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            enabled = enabled,
            singleLine = true
        )
    }
}