package com.example.villa.service_api

import com.example.villa.model.Review
import com.example.villa.model.ReviewResponse
import com.example.villa.model.ReviewResponseDetail
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReviewService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("review")
    suspend fun getReview(): ReviewResponse

    @GET("review/{id_review}")
    suspend fun getReviewById(@Path("id_review") id_review:Int): ReviewResponseDetail

    @POST("review")
    suspend fun insertReview(@Body review: Review): Review

    @PUT("review/{id_review}")
    suspend fun updateReview(@Path("id_review") id_review: Int, @Body review: Review): Review

}