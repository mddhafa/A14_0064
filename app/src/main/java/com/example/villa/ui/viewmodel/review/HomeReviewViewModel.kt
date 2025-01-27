package com.example.villa.ui.viewmodel.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.villa.model.Pelanggan
import com.example.villa.model.Reservasi
import com.example.villa.model.Review
import com.example.villa.model.ReviewResponseDetail
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.ReviewRepository
import com.example.villa.ui.viewmodel.reservasi.HomeReservasiUiState
import kotlinx.coroutines.launch
import okio.IOException


sealed class HomeReviewUiState{
    data class Success(val review: List<Review>, val reservasi: List<Reservasi>, val pelangan: List<Pelanggan>): HomeReviewUiState()
    object Error : HomeReviewUiState()
    object Loading : HomeReviewUiState()
}

class HomeReviewViewModel (
    private val reviewRepository: ReviewRepository,
    private val reservasiVillaRepository: ReservasiVillaRepository,
    private val pelangganRepository: PelangganRepository
): ViewModel(){
    var reviewUiState: HomeReviewUiState by mutableStateOf(HomeReviewUiState.Loading)
        private set

    init {
        getReview()
    }
    fun getReview() {
        viewModelScope.launch {
            reviewUiState = HomeReviewUiState.Loading
            try {
                val review = reviewRepository.getReview().data
                val reservasi = reservasiVillaRepository.getReservasi().data
                val pelanggan = pelangganRepository.getPelanggan().data
                reviewUiState = HomeReviewUiState.Success(review, reservasi,pelanggan)
            } catch (e: IOException) {
                reviewUiState = HomeReviewUiState.Error
            } catch (e: HttpException) {
                reviewUiState = HomeReviewUiState.Error
            }
        }
    }

}