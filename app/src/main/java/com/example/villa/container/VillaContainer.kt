package com.example.villa.container

import com.example.villa.repository.NetworkPelangganRepository
import com.example.villa.repository.NetworkReservasiRepository
import com.example.villa.repository.NetworkReviewRepository
import com.example.villa.repository.NetworkVillaRepository
import com.example.villa.repository.PelangganRepository
import com.example.villa.repository.ReservasiVillaRepository
import com.example.villa.repository.ReviewRepository
import com.example.villa.repository.VillaRepository
import com.example.villa.service_api.PelangganService
import com.example.villa.service_api.ReservasiService
import com.example.villa.service_api.ReviewService
import com.example.villa.service_api.VillaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val villaRepository: VillaRepository
    val pelangganRepository: PelangganRepository
    val reservasiRepository: ReservasiVillaRepository
    val reviewRepository: ReviewRepository
}

class VillaContainer : AppContainer{

    private val baseUrl = "http://10.0.2.2:3000/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val villaService: VillaService by lazy {
        retrofit.create(VillaService::class.java)
    }
    private val pelangganService: PelangganService by lazy {
        retrofit.create(PelangganService::class.java)
    }
    private val reservasiService: ReservasiService by lazy {
        retrofit.create(ReservasiService::class.java)
    }
    private val reviewService: ReviewService by lazy {
        retrofit.create(ReviewService::class.java)
    }

    override val villaRepository: VillaRepository by lazy {
        NetworkVillaRepository(villaService)
    }

    override val pelangganRepository: PelangganRepository by lazy {
        NetworkPelangganRepository(pelangganService)
    }

    override val reservasiRepository: ReservasiVillaRepository by lazy {
        NetworkReservasiRepository(reservasiService)
    }

    override val reviewRepository: ReviewRepository by lazy {
        NetworkReviewRepository(reviewService)
    }

}