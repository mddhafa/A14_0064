package com.example.villa.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Pelanggan
import com.example.villa.model.Reservasi
import com.example.villa.model.Villa
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.VillaRepository
import com.example.villa.ui.view.reservasi.DestinasiDetailReservasi
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailReservasiUiState{
    data class Success(val reservasi: Reservasi, val villa: Villa, val pelanggan: Pelanggan) : DetailReservasiUiState()
    object Error : DetailReservasiUiState()
    object Loading : DetailReservasiUiState()
}

class DetailReservasiViewModel(
    savedStateHandle: SavedStateHandle,
    private val rsv: ReservasiVillaRepository,
    private val vla: VillaRepository,
    private val plg: PelangganRepository
): ViewModel(){

    var reservasiDetailUiState: DetailReservasiUiState by mutableStateOf(DetailReservasiUiState.Loading)
        private set

    private val idr: Int = checkNotNull(savedStateHandle[DestinasiDetailReservasi.ID_Reservasi])

    init {
        getReservasiById()
        println(idr)
    }

    fun getReservasiById(){
        viewModelScope.launch {
            reservasiDetailUiState = DetailReservasiUiState.Loading
            reservasiDetailUiState = try {
                val reservasi = rsv.getReservasiById(idr).data
                val villa = vla.getVillaById(reservasi.id_villa).data
                val pelanggan = plg.getPelangganById(reservasi.id_pelanggan).data
                DetailReservasiUiState.Success(reservasi, villa, pelanggan)
            }catch (e: IOException){
                DetailReservasiUiState.Error
            }catch (e: IOException){
                DetailReservasiUiState.Error
            }
        }
    }
}