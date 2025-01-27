package com.example.villa.service_api

import com.example.villa.model.Reservasi
import com.example.villa.model.ReservasiResponse
import com.example.villa.model.ReservasiResponseDetail
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("reservasi")
    suspend fun getReservasi(): ReservasiResponse

    @GET("reservasi/{id_reservasi}")
    suspend fun getReservasiById(@Path("id_reservasi") id_reservasi:Int): ReservasiResponseDetail

    @POST("reservasi")
    suspend fun insertReservasi(@Body reservasi: Reservasi): Reservasi

    @PUT("reservasi/{id_reservasi}")
    suspend fun updateReservasi(@Path("id_reservasi") id_reservasi: Int, @Body reservasi: Reservasi): Reservasi

    @DELETE("reservasi/{id_reservasi}")
    suspend fun deleteReservasi(@Path("id_reservasi") id_reservasi: Int): retrofit2.Response<Void>
}