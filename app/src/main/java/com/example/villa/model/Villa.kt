package com.example.villa.model

import kotlinx.serialization.Serializable

@Serializable
data class VillaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Villa>
)

@Serializable
data class VillaResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Villa
)

@Serializable
data class  Villa (
    val id_villa: Int,
    val nama_villa: String,
    val alamat: String,
    var kamar_tersedia: Int,
    val review: List<Review> = emptyList()
)
