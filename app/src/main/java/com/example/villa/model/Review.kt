package com.example.villa.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val id_review: Int,
    val id_reservasi: Int,
    val nilai: String,
    val komentar: String? = null
)

@Serializable
data class ReviewResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Review
)

@Serializable
data class ReviewResponse(
    val status: Boolean,
    val message: String,
    val data: List<Review>
)
