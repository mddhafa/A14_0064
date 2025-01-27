package com.example.villa.ui.viewmodel.review

import com.example.villa.ui.viewmodel.reservasi.InsertReservasiUiEvent
import com.example.villa.ui.viewmodel.reservasi.InsertReservasiUiState
import com.example.villa.ui.viewmodel.reservasi.toDropdownOption
import com.example.villa.ui.viewmodel.reservasi.toReservasi
import com.example.villa.ui.viewmodel.reservasi.toUiStateReservasi
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.ReviewRepository
import com.example.villa.ui.view.review.DestinasiUpdateReview
import kotlinx.coroutines.launch
import kotlinx.serialization.MissingFieldException
import retrofit2.HttpException


class UpdateReviewViewModel(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository,
    private val reservasiVillaRepository: ReservasiVillaRepository
): ViewModel() {

    var updateRevUiState by mutableStateOf(InsertReviewUiState())
        private set

    private val id_review: Int = checkNotNull(savedStateHandle[DestinasiUpdateReview.ID_Review])

    init {
        loadReviewData()
        loadReservasiData()
    }

    private fun loadReviewData() {
        viewModelScope.launch {
            try {
                val reviewResponse = reviewRepository.getReviewById(id_review)

                updateRevUiState = reviewResponse.data.toUiStateReview()
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



    private fun loadReservasiData() {
        viewModelScope.launch {
            try {
                val reservasiResponse = reservasiVillaRepository.getReservasi()

                val reservasiList = reservasiResponse.data

                updateRevUiState = updateRevUiState.copy(
                    reservasiOptions = reservasiList.map { it.toDropdownOption() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertRevState(event: InsertReviewUiEvent){
        updateRevUiState = updateRevUiState.copy(insertReviewUiEvent = event)
    }

    fun updateRev() {
        viewModelScope.launch {
            try {
                val review = updateRevUiState.insertReviewUiEvent.toReview()

                reviewRepository.updateReview(id_review, review )

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