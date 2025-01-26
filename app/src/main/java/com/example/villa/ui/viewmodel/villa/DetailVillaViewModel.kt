package com.example.villa.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.villa.model.Review
import com.example.villa.model.Villa
import com.example.villa.repository.VillaRepository
import com.example.villa.ui.view.villa.DestinasiDetailVilla
import kotlinx.coroutines.launch
import okio.IOException


sealed class DetailVillaUiState{
    data class Success(val villa: Villa, val review: List<Review>) : DetailVillaUiState()
    object Error : DetailVillaUiState()
    object Loading : DetailVillaUiState()
}
class DetailVillaViewModel(
    savedStateHandle: SavedStateHandle,
    private val vla: VillaRepository,
) : ViewModel() {

    var villaDetailState: DetailVillaUiState by mutableStateOf(DetailVillaUiState.Loading)
        private set

    private val id: Int = checkNotNull(savedStateHandle[DestinasiDetailVilla.ID_Villa])

    init {
        getVillaById()
        println(id)
    }

    fun getVillaById() {
        viewModelScope.launch {
            villaDetailState = DetailVillaUiState.Loading
            villaDetailState = try {
                val villaResponse = vla.getVillaById(id)
                val villa = villaResponse.data
                val review = villa.Review
                DetailVillaUiState.Success(villa, review)
            } catch (e: IOException) {
                DetailVillaUiState.Error
            } catch (e: HttpException) {
                DetailVillaUiState.Error
            }
        }
    }

    fun deleteVilla(idVilla: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                vla.deleteVilla(idVilla)
                onSuccess()
            } catch (e: IOException) {
                onError()
            } catch (e: HttpException) {
                onError()
            }
        }
    }
}
