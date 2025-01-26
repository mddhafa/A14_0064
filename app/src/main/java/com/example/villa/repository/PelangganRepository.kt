package com.example.villa.repository


import com.example.villa.model.Pelanggan
import com.example.villa.model.PelangganResponse
import com.example.villa.model.PelangganResponseDetail
import com.example.villa.service_api.PelangganService

interface PelangganRepository{
    suspend fun getPelanggan(): PelangganResponse

    suspend fun insertPelanggan(pelanggan: Pelanggan)

    suspend fun updatePelanggan(id_pelanggan: Int, pelanggan: Pelanggan)

    suspend fun deletePelanggan(id_pelanggan: Int)

    suspend fun getPelangganById(id_pelanggan: Int): PelangganResponseDetail

}

class NetworkPelangganRepository(
    private val pelangganService: PelangganService
): PelangganRepository {
    override suspend fun insertPelanggan(pelanggan: Pelanggan) {
        pelangganService.insertPelanggan(pelanggan)
    }

    override suspend fun updatePelanggan(id_pelanggan: Int, pelanggan: Pelanggan) {
        pelangganService.updatePelanggan(id_pelanggan,pelanggan)
    }

    override suspend fun deletePelanggan(id_pelanggan: Int) {
        try {
            val response = pelangganService.deletePelanggan(id_pelanggan)

            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                throw Exception("Failed to delete villa: $errorBody")
            }
        } catch (e: Exception) {
            println("Error deleting villa with ID $id_pelanggan: ${e.message}")
            throw e
        }
    }

    override suspend fun getPelangganById(id_pelanggan: Int): PelangganResponseDetail {
        return pelangganService.getPelangganById(id_pelanggan)
    }

    override suspend fun getPelanggan(): PelangganResponse {
        return  pelangganService.getPelanggan()
    }
}