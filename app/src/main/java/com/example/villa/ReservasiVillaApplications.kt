package com.example.villa

import android.app.Application
import com.example.villa.container.AppContainer
import com.example.villa.container.VillaContainer

class ReservasiVillaApplications: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = VillaContainer()
    }
}