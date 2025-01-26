package com.example.villa.ui.viewmodel.pelanggan

import androidx.lifecycle.ViewModel
import com.example.villa.model.Villa
import com.example.villa.repository.VillaRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Pelanggan
import com.example.villa.repository.PelangganRepository
import kotlinx.coroutines.launch

class InsertPelangganViewModel (private val plg: PelangganRepository): ViewModel(){
    var uiPelangganState by mutableStateOf(InsertPelangganUiState())
        private set

    fun updateInsertPelangganState(insertPelangganUiEvent: InsertPelangganUiEvent){
        uiPelangganState = InsertPelangganUiState(insertPelangganUiEvent = insertPelangganUiEvent)
    }

    suspend fun insertPlg(){
        viewModelScope.launch {
            try {
                plg.insertPelanggan(uiPelangganState.insertPelangganUiEvent.toPelanggan())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun Pelanggan.toUiStatePelanggan(): InsertPelangganUiState = InsertPelangganUiState(
    insertPelangganUiEvent = toInsertPelangganUiEvent()
)

fun Pelanggan.toInsertPelangganUiEvent(): InsertPelangganUiEvent = InsertPelangganUiEvent(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)

fun InsertPelangganUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)

data class InsertPelangganUiState(
    val insertPelangganUiEvent: InsertPelangganUiEvent = InsertPelangganUiEvent()
)

data class InsertPelangganUiEvent(
    val id_pelanggan: Int = 0,
    val nama_pelanggan: String = "",
    val no_hp: String = "",
)