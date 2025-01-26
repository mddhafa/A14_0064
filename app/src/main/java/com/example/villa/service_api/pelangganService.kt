package com.example.villa.service_api

import com.example.villa.model.Pelanggan
import com.example.villa.model.PelangganResponse
import com.example.villa.model.PelangganResponseDetail
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PelangganService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("pelanggan")
    suspend fun getPelanggan(): PelangganResponse

    @GET("pelanggan/{id_pelanggan}")
    suspend fun getPelangganById(@Path("id_pelanggan") id_pelanggan:Int): PelangganResponseDetail

    @POST("pelanggan")
    suspend fun insertPelanggan(@Body pelanggan: Pelanggan): Pelanggan

    @PUT("pelanggan/{id_pelanggan}")
    suspend fun updatePelanggan(@Path("id_pelanggan") id_pelanggan: Int, @Body pelanggan: Pelanggan): Pelanggan

    @DELETE("pelanggan/{id_pelanggan}")
    suspend fun deletePelanggan(@Path("id_pelanggan") id_pelanggan: Int): retrofit2.Response<Void>
}