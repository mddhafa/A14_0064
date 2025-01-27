package com.example.villa.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.villa.model.Pelanggan
import com.example.villa.model.PelangganResponse
import com.example.villa.model.Reservasi
import com.example.villa.model.ReservasiResponse
import com.example.villa.model.Review
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.ReviewRepository
import com.example.villa.ui.navigation.DropdownOption
import kotlinx.coroutines.launch


class InsertReviewViewModel (
    private val rev: ReviewRepository,
    private val res: ReservasiVillaRepository
): ViewModel(){
    var uiReviewState by mutableStateOf(InsertReviewUiState())
        private set

    init {
        loadPelangganData()
    }

    private fun loadPelangganData() {
        viewModelScope.launch {
            try {

                val reservasiResponse: ReservasiResponse = res.getReservasi()

                val reservasiList: List<Reservasi> = reservasiResponse.data
                uiReviewState = uiReviewState.copy(
                    reservasiOptions = reservasiList.map { it.toDropdownOption() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertReviewState(event: InsertReviewUiEvent) {
        uiReviewState = uiReviewState.copy(insertReviewUiEvent = event)
    }

    suspend fun insertRev(){
        viewModelScope.launch {
            try {
                rev.insertReview(uiReviewState.insertReviewUiEvent.toReview())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

fun Review.toUiStateReview(): InsertReviewUiState = InsertReviewUiState(
    insertReviewUiEvent = toInsertReviewUiEvent()
)

fun Review.toInsertReviewUiEvent(): InsertReviewUiEvent = InsertReviewUiEvent(
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
)

fun InsertReviewUiEvent.toReview(): Review = Review(
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
    komentar = komentar
)


fun Reservasi.toDropdownOption() = DropdownOption(id = id_reservasi,  label = "Reservasi #$id_reservasi" )

data class InsertReviewUiState(
    val insertReviewUiEvent: InsertReviewUiEvent = InsertReviewUiEvent(),
    val villaOptions: List<DropdownOption> = emptyList(),
    val reservasiOptions: List<DropdownOption> = emptyList()
)


data class DropdownOption(
    val id: Int,
    val label: String
)

data class InsertReviewUiEvent(
    val id_review: Int = 0,
    val id_reservasi: Int = 0,
    val nilai: String = "",
    val komentar: String = "",
)