package com.example.villa.repository

import com.example.villa.model.Reservasi
import com.example.villa.model.ReservasiResponse
import com.example.villa.model.ReservasiResponseDetail
import com.example.villa.service_api.ReservasiService


interface ReservasiVillaRepository{
    suspend fun getReservasi(): ReservasiResponse

    suspend fun insertReservasi(reservasi: Reservasi)

    suspend fun updateReservasi(id_reservasi: Int, reservasi: Reservasi )

    suspend fun deleteReservasi(id_reservasi: Int)

    suspend fun getReservasiById(id_reservasi: Int): ReservasiResponseDetail
}

class NetworkReservasiRepository(
    private val reservasiService: ReservasiService
): ReservasiVillaRepository {
    override suspend fun getReservasi(): ReservasiResponse {
        return reservasiService.getReservasi()
    }

    override suspend fun getReservasiById(id_reservasi: Int): ReservasiResponseDetail {
        return reservasiService.getReservasiById(id_reservasi)
    }

    override suspend fun insertReservasi(reservasi: Reservasi){
        reservasiService.insertReservasi(reservasi)
    }

    override suspend fun updateReservasi(id_reservasi: Int, reservasi: Reservasi) {
        reservasiService.updateReservasi(id_reservasi, reservasi)
    }

    override suspend fun deleteReservasi(id_reservasi: Int) {
        try {
            val response = reservasiService.deleteReservasi(id_reservasi)

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                throw Exception("Failed to delete villa: $errorBody")
            }
        } catch (e: Exception) {
            println("Error deleting villa with ID $id_reservasi: ${e.message}")
            throw e
        }
    }
}