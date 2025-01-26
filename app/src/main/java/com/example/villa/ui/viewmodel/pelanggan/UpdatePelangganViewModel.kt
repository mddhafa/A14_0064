package com.example.villa.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.repository.PelangganRepository
import com.example.villa.ui.view.pelanggan.DestinasiUpdatePelanggan
import kotlinx.coroutines.launch


class UpdatePelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
): ViewModel() {

    var updatePlgUiState by mutableStateOf(InsertPelangganUiState())
        private set

    private val id_pelanggan: Int = checkNotNull(savedStateHandle[DestinasiUpdatePelanggan.ID_Pelanggan])

    init {
        viewModelScope.launch {
            updatePlgUiState = pelangganRepository.getPelangganById(id_pelanggan).data
                .toUiStatePelanggan()
        }
    }

    fun updateInsertPlgState(insertPelangganUiEvent: InsertPelangganUiEvent) {
        updatePlgUiState = InsertPelangganUiState(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    suspend fun updatePlg(){
        viewModelScope.launch {
            try {
                pelangganRepository.updatePelanggan(id_pelanggan, updatePlgUiState.insertPelangganUiEvent.toPelanggan())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}