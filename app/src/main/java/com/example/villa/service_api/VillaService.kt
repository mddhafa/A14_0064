package com.example.villa.service_api

import com.example.villa.model.Villa
import com.example.villa.model.VillaResponse
import com.example.villa.model.VillaResponseDetail
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VillaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("villa")
    suspend fun getVilla(): VillaResponse

    @GET("villa/{id_villa}")
    suspend fun getVillaById(@Path("id_villa") id_villa:Int): VillaResponseDetail

    @POST("villa")
    suspend fun insertVilla(@Body villa: Villa): Villa

    @PUT("villa/{id_villa}")
    suspend fun updateVilla(@Path("id_villa") id_villa: Int, @Body villa: Villa): Villa

    @DELETE("villa/{id_villa}")
    suspend fun deleteVilla(@Path("id_villa") id_villa: Int)

}