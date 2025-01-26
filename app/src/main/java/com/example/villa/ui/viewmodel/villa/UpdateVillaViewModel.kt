package com.example.villa.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.repository.VillaRepository
import com.example.villa.ui.view.villa.DestinasiUpdateVilla
import kotlinx.coroutines.launch


class UpdateVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val villaRepository: VillaRepository,
): ViewModel() {

    var updateVlaUiState by mutableStateOf(InsertVillaUiState())
        private set

    private val id: Int = checkNotNull(savedStateHandle[DestinasiUpdateVilla.IDVilla])

    init {
        viewModelScope.launch {
            updateVlaUiState = villaRepository.getVillaById(id).data
                .toUiStateVilla()
        }
    }
    fun updateInsertVlaState(insertVillaUiEvent: InsertVillaUiEvent) {
        updateVlaUiState = InsertVillaUiState(insertVillaUiEvent = insertVillaUiEvent)
    }

    suspend fun updateVla(){
        viewModelScope.launch {
            try {
                villaRepository.updateVilla(id, updateVlaUiState.insertVillaUiEvent.toVilla())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}