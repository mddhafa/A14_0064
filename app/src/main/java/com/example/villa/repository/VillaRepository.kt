package com.example.villa.repository

import com.example.villa.model.Villa
import com.example.villa.model.VillaResponse
import com.example.villa.model.VillaResponseDetail
import com.example.villa.service_api.VillaService

interface VillaRepository{
    suspend fun getVilla(): VillaResponse

    suspend fun insertVilla(villa: Villa)

    suspend fun updateVilla(id_villa: Int, villa: Villa)

    suspend fun deleteVilla(id_villa: Int)

    suspend fun getVillaById(id_villa: Int): VillaResponseDetail

}

class NetworkVillaRepository(
    private val villaService: VillaService
): VillaRepository {
    override suspend fun insertVilla(villa: Villa) {
        villaService.insertVilla(villa)
    }

    override suspend fun updateVilla(id_villa: Int, villa: Villa) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteVilla(id_villa: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getVillaById(id_villa: Int): VillaResponseDetail {
        return villaService.getVillaById(id_villa)
    }

    override suspend fun getVilla(): VillaResponse {
        return villaService.getVilla()
    }
}