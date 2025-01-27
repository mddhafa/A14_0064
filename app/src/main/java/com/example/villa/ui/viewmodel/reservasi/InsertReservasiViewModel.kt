package com.example.villa.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Pelanggan
import com.example.villa.model.PelangganResponse
import com.example.villa.model.Reservasi
import com.example.villa.model.Villa
import com.example.villa.model.VillaResponse
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.VillaRepository
import com.example.villa.ui.navigation.DropdownOption
import kotlinx.coroutines.launch


class InsertReservasiViewModel (
    private val rsv: ReservasiVillaRepository,
    private val vla: VillaRepository,
    private val plg: PelangganRepository
): ViewModel(){
    var uiReservasiState by mutableStateOf(InsertReservasiUiState())
        private set

    init {
        loadVillaAndPelangganData()
    }

    private fun loadVillaAndPelangganData() {
        viewModelScope.launch {
            try {
                val villaResponse: VillaResponse = vla.getVilla()
                val pelangganResponse: PelangganResponse = plg.getPelanggan()

                val villaList: List<Villa> = villaResponse.data
                val pelangganList: List<Pelanggan> = pelangganResponse.data

                uiReservasiState = uiReservasiState.copy(
                    villaOptions = villaList.map { it.toDropdownOption() },
                    pelangganOptions = pelangganList.map { it.toDropdownOption() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertReservasiState(event: InsertReservasiUiEvent) {
        uiReservasiState = uiReservasiState.copy(insertReservasiUiEvent = event)
    }

    suspend fun insertRsv(navigateBack: () -> Unit) {
        viewModelScope.launch {
            try {
                val selectedVilla = uiReservasiState.villaOptions.find { it.id == uiReservasiState.insertReservasiUiEvent.id_villa }
                if (selectedVilla != null) {
                    val villaDetail = vla.getVillaById(selectedVilla.id)
                    val availableRooms = villaDetail.data.kamar_tersedia

                    if (availableRooms >= uiReservasiState.insertReservasiUiEvent.jumlah_kamar) {
                        rsv.insertReservasi(uiReservasiState.insertReservasiUiEvent.toReservasi())

                        uiReservasiState = uiReservasiState.copy(isSuccess = true)
                        navigateBack()
                    } else {
                        uiReservasiState = uiReservasiState.copy(
                            errorMessage = "Kamar Tidak Tersedia. Hanya $availableRooms Kamar tersisa ."
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                uiReservasiState = uiReservasiState.copy(
                    errorMessage = "Terjadi kesalahan. Silakan coba lagi."
                )
            }
        }
    }

}

fun Reservasi.toUiStateReservasi(): InsertReservasiUiState = InsertReservasiUiState(
    insertReservasiUiEvent = toInsertReservasiUiEvent()
)

fun Reservasi.toInsertReservasiUiEvent(): InsertReservasiUiEvent = InsertReservasiUiEvent(
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar
)

fun InsertReservasiUiEvent.toReservasi(): Reservasi = Reservasi(
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar
)
fun Villa.toDropdownOption() = DropdownOption(id = id_villa, label = nama_villa)
fun Pelanggan.toDropdownOption() = DropdownOption(id = id_pelanggan, label = nama_pelanggan)

data class InsertReservasiUiState(
    val insertReservasiUiEvent: InsertReservasiUiEvent = InsertReservasiUiEvent(),
    val villaOptions: List<DropdownOption> = emptyList(),
    val pelangganOptions: List<DropdownOption> = emptyList(),
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)


data class DropdownOption(
    val id: Int,
    val label: String
)

data class InsertReservasiUiEvent(
    val id_reservasi: Int = 0,
    val id_villa: Int = 0,
    val id_pelanggan: Int = 0,
    val check_in: String = "",
    val check_out: String = "",
    val jumlah_kamar: Int = 0
)

