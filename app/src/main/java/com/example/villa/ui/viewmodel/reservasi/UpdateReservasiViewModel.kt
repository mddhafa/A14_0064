package com.example.villa.ui.viewmodel.reservasi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.VillaRepository
import com.example.villa.ui.view.reservasi.DestinasiUpdateReservasi
import kotlinx.coroutines.launch
import kotlinx.serialization.MissingFieldException
import retrofit2.HttpException


class UpdateReservasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val reservasiVillaRepository: ReservasiVillaRepository,
    private val villaRepository: VillaRepository,
    private val pelangganRepository: PelangganRepository
): ViewModel() {

    var updateRsvUiState by mutableStateOf(InsertReservasiUiState())
        private set

    private val id_reservasi: Int = checkNotNull(savedStateHandle[DestinasiUpdateReservasi.ID_Reservasi])

    init {
        loadReservasiData()
        loadVillaAndPelangganData()
    }

    private fun loadReservasiData() {
        viewModelScope.launch {
            try {
                val reservasiResponse = reservasiVillaRepository.getReservasiById(id_reservasi)
                updateRsvUiState = reservasiResponse.data.toUiStateReservasi()
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    println("Error 404: Endpoint tidak ditemukan.")
                } else {
                    println("HTTP error: ${e.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error: ${e.message}")
            }
        }
    }



    private fun loadVillaAndPelangganData() {
        viewModelScope.launch {
            try {
                val villaResponse = villaRepository.getVilla()
                val pelangganResponse = pelangganRepository.getPelanggan()

                val villaList = villaResponse.data
                val pelangganList = pelangganResponse.data

                updateRsvUiState = updateRsvUiState.copy(
                    villaOptions = villaList.map { it.toDropdownOption() },
                    pelangganOptions = pelangganList.map { it.toDropdownOption() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertRsvState(event: InsertReservasiUiEvent) {
        updateRsvUiState = updateRsvUiState.copy(insertReservasiUiEvent = event)
    }

    fun updateRsv() {
        viewModelScope.launch {
            try {
                val reservasi = updateRsvUiState.insertReservasiUiEvent.toReservasi()

                reservasiVillaRepository.updateReservasi(id_reservasi, reservasi)

            } catch (e: HttpException) {
                if (e.code() == 404) {
                } else {
                    Log.e("UpdateReservasiViewModel", "HTTP error: ${e.message()}")
                }
            } catch (e: Exception) {
                Log.e("UpdateReservasiViewModel", "Error saat mengupdate reservasi: ${e.message}", e)
            }
        }
    }

}