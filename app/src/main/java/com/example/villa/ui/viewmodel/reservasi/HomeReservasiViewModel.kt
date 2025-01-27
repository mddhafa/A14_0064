package com.example.villa.ui.viewmodel.reservasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.villa.model.Reservasi
import com.example.villa.model.Villa
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.VillaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeReservasiUiState{
    data class Success(val reservasi: List<Reservasi>, val villa: List<Villa>): HomeReservasiUiState()
    object Error : HomeReservasiUiState()
    object Loading : HomeReservasiUiState()
}

class HomeReservasiViewModel(
    private val reservasiVillaRepository: ReservasiVillaRepository,
    private val villaRepository: VillaRepository
): ViewModel(){
    var reservasiUiState: HomeReservasiUiState by mutableStateOf(HomeReservasiUiState.Loading)
        private set

    init {
        getReservasi()
    }

    fun getReservasi(){
        viewModelScope.launch {
            reservasiUiState = HomeReservasiUiState.Loading
            try {
                // Fetch data from both repositories
                val reservasi = reservasiVillaRepository.getReservasi().data
                val villa = villaRepository.getVilla().data
                reservasiUiState = HomeReservasiUiState.Success(reservasi, villa)
            } catch (e: IOException) {
                reservasiUiState = HomeReservasiUiState.Error
            } catch (e: HttpException) {
                reservasiUiState = HomeReservasiUiState.Error
            }
        }
    }
    fun deleteReservasi(id_reservasi: Int){
        viewModelScope.launch {
            try {
                reservasiVillaRepository.deleteReservasi(id_reservasi)
            }catch (e: IOException){
                HomeReservasiUiState.Error
            }catch (e: IOException){
                HomeReservasiUiState.Error
            }
        }
    }
}