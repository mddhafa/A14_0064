package com.example.villa.ui.viewmodel.pelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Pelanggan
import com.example.villa.repository.PelangganRepository
import com.example.villa.ui.view.pelanggan.DestinasiDetaiPelanggan
import kotlinx.coroutines.launch
import okio.IOException


sealed class DetailPelangganUiState{
    data class Success(val pelanggan: Pelanggan) : DetailPelangganUiState()
    object Error : DetailPelangganUiState()
    object Loading : DetailPelangganUiState()
}

class DetailPelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val plg: PelangganRepository
): ViewModel(){

    var pelangganUiState: DetailPelangganUiState by mutableStateOf(DetailPelangganUiState.Loading)
        private set

    private val idplg: Int = checkNotNull(savedStateHandle[DestinasiDetaiPelanggan.ID_Pelanggan])

    init {
        getPelangganById()
        println(idplg)
    }
    fun getPelangganById(){
        viewModelScope.launch {
            pelangganUiState = DetailPelangganUiState.Loading
            pelangganUiState = try {
                val pelanggan = plg.getPelangganById(idplg)
                DetailPelangganUiState.Success(pelanggan.data)
            }catch (e: IOException){
                DetailPelangganUiState.Error
            }catch (e: IOException) {
                DetailPelangganUiState.Error
            }
        }
    }

    fun deletePelanggan(id_pelanggan: Int, onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                plg.deletePelanggan(id_pelanggan)
            }catch (e: IOException){
                HomePelangganUiState.Error
            }catch (e: IOException){
                HomePelangganUiState.Error
            }
        }
    }
}