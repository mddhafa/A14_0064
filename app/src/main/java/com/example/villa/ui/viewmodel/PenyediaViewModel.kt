package com.example.villa.ui.viewmodel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.villa.ReservasiVillaApplications
import com.example.villa.repository.PelangganRepository
import com.example.villa.ui.viewmodel.pelanggan.DetailPelangganViewModel
import com.example.villa.ui.viewmodel.pelanggan.HomePelangganViewModel
import com.example.villa.ui.viewmodel.pelanggan.InsertPelangganViewModel
import com.example.villa.ui.viewmodel.pelanggan.UpdatePelangganViewModel
import com.example.villa.ui.viewmodel.reservasi.DetailReservasiViewModel
import com.example.villa.ui.viewmodel.reservasi.HomeReservasiViewModel
import com.example.villa.ui.viewmodel.reservasi.InsertReservasiViewModel
import com.example.villa.ui.viewmodel.reservasi.UpdateReservasiViewModel
import com.example.villa.ui.viewmodel.review.HomeReviewViewModel
import com.example.villa.ui.viewmodel.review.InsertReviewViewModel
import com.example.villa.ui.viewmodel.review.UpdateReviewViewModel
import com.example.villa.ui.viewmodel.villa.DetailVillaViewModel
import com.example.villa.ui.viewmodel.villa.HomeVillaViewModel
import com.example.villa.ui.viewmodel.villa.InsertVillaViewModel
import com.example.villa.ui.viewmodel.villa.UpdateVillaViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        //Villa
        initializer { HomeVillaViewModel(aplikasiVilla().container.villaRepository)}
        initializer { InsertVillaViewModel(aplikasiVilla().container.villaRepository)}
        initializer { DetailVillaViewModel(createSavedStateHandle(),aplikasiVilla().container.villaRepository) }
        initializer { UpdateVillaViewModel(createSavedStateHandle(),aplikasiVilla().container.villaRepository) }

        //Pelanggan
        initializer { HomePelangganViewModel(aplikasiVilla().container.pelangganRepository) }
        initializer { InsertPelangganViewModel(aplikasiVilla().container.pelangganRepository) }
        initializer { DetailPelangganViewModel(createSavedStateHandle(),aplikasiVilla().container.pelangganRepository) }
        initializer { UpdatePelangganViewModel(createSavedStateHandle(),aplikasiVilla().container.pelangganRepository) }


    }
}

fun CreationExtras.aplikasiVilla(): ReservasiVillaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReservasiVillaApplications)