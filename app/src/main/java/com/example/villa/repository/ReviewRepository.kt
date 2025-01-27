package com.example.villa.repository

import com.example.villa.model.Review
import com.example.villa.model.ReviewResponse
import com.example.villa.model.ReviewResponseDetail
import com.example.villa.service_api.ReviewService


interface ReviewRepository{

    suspend fun getReview(): ReviewResponse

    suspend fun insertReview(review: Review)

    suspend fun updateReview(id_review: Int, review: Review)


    suspend fun getReviewById(id_review: Int): ReviewResponseDetail

}

class NetworkReviewRepository(
    private val reviewService: ReviewService
): ReviewRepository {
    override suspend fun insertReview(review: Review) {
        reviewService.insertReview(review)
    }

    override suspend fun updateReview(id_review: Int, review: Review) {
        reviewService.updateReview(id_review, review)
    }

    override suspend fun getReviewById(id_review: Int): ReviewResponseDetail {
        return reviewService.getReviewById(id_review)
    }

    override suspend fun getReview(): ReviewResponse {
        return  reviewService.getReview()
    }
}