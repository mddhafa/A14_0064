package com.example.villa.ui.viewmodel

import android.text.Editable.Factory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.villa.ReservasiVillaApplications
import com.example.villa.ui.viewmodel.villa.DetailVillaViewModel
import com.example.villa.ui.viewmodel.villa.HomeVillaViewModel
import com.example.villa.ui.viewmodel.villa.InsertVillaViewModel
import com.example.villa.ui.viewmodel.villa.UpdateVillaViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeVillaViewModel(aplikasiVilla().container.villaRepository)}

}

fun CreationExtras.aplikasiVilla(): ReservasiVillaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ReservasiVillaApplications)