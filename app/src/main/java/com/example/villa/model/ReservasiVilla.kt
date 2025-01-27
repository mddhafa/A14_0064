package com.example.villa.model

import kotlinx.serialization.Serializable


@Serializable
data class ReservasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Reservasi>
)

@Serializable
data class ReservasiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Reservasi
)


@Serializable
data class Reservasi(
    val id_reservasi: Int = 0,
    val id_villa: Int = 0,
    val id_pelanggan: Int = 0,
    val check_in: String = "",
    val check_out: String = "",
    val jumlah_kamar: Int = 0
)

