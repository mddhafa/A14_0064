package com.example.villa.ui.viewmodel.villa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.villa.model.Villa
import com.example.villa.repository.VillaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeVillaUiState{
    data class Success(val villa: List<Villa>): HomeVillaUiState()
    object Error : HomeVillaUiState()
    object Loading : HomeVillaUiState()
}

class HomeVillaViewModel (
    private val villa: VillaRepository
): ViewModel(){
    var villaUiState: HomeVillaUiState by mutableStateOf(HomeVillaUiState.Loading)
        private set

    init {
        getVilla()
    }

    fun getVilla(){
        viewModelScope.launch {
            villaUiState = HomeVillaUiState.Loading
            villaUiState = try {
                HomeVillaUiState.Success(villa.getVilla().data)
            } catch (e: IOException){
                HomeVillaUiState.Error
            } catch (e: HttpException){
                HomeVillaUiState.Error
            }
        }
    }

}