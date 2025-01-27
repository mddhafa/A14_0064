package com.example.villa.ui.viewmodel.villa

import androidx.lifecycle.ViewModel
import com.example.villa.model.Villa
import com.example.villa.repository.VillaRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Review
import kotlinx.coroutines.launch

class InsertVillaViewModel (private val vla: VillaRepository): ViewModel(){
    var uiVillaState by mutableStateOf(InsertVillaUiState())
        private set

    fun updateInsertVillaState(insertVillaUiEvent: InsertVillaUiEvent){
        uiVillaState = InsertVillaUiState(insertVillaUiEvent = insertVillaUiEvent)
    }

    suspend fun insertVla(){
        viewModelScope.launch {
            try {
                vla.insertVilla(uiVillaState.insertVillaUiEvent.toVilla())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

fun Villa.toUiStateVilla(): InsertVillaUiState = InsertVillaUiState(
    insertVillaUiEvent = toInsertVillaUiEvent()
)

fun Villa.toInsertVillaUiEvent(): InsertVillaUiEvent = InsertVillaUiEvent(
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia,
)

fun InsertVillaUiEvent.toVilla(): Villa = Villa(
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia,
)

data class InsertVillaUiState(
    val insertVillaUiEvent: InsertVillaUiEvent = InsertVillaUiEvent()
)

data class InsertVillaUiEvent(
    val id_villa: Int = 0,
    val nama_villa: String ="",
    val alamat: String = "",
    val kamar_tersedia: Int = 0,
    val review: List<Review> = emptyList() // Default kosong
)