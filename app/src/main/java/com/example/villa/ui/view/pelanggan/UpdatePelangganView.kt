package com.example.villa.ui.view.pelanggan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.villa.ui.customwidget.CostumeTopAppBar
import com.example.villa.ui.navigation.DestinasiNavigasi
import com.example.villa.ui.viewmodel.PenyediaViewModel
import com.example.villa.ui.viewmodel.pelanggan.UpdatePelangganViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePelanggan : DestinasiNavigasi{
    override val route = "updatepelanggan"
    override val titleRes = "Update Pelanggan"
    const val ID_Pelanggan = "id_pelanggan"
    val routesWithArg = "$route/{$ID_Pelanggan}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePelangganScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdatePelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePelanggan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyPelanggan(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updatePlgUiState,
            onPelangganValueChange = viewModel::updateInsertPlgState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePlg()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}